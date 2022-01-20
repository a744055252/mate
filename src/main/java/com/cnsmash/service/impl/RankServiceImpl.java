package com.cnsmash.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cnsmash.exception.CodeException;
import com.cnsmash.exception.ErrorCode;
import com.cnsmash.mapper.RankLogMapper;
import com.cnsmash.mapper.UserRankMapper;
import com.cnsmash.pojo.BattleResultType;
import com.cnsmash.pojo.RankChangeType;
import com.cnsmash.pojo.entity.Quarter;
import com.cnsmash.pojo.entity.RankLog;
import com.cnsmash.pojo.entity.UserRank;
import com.cnsmash.pojo.ro.PageFighterRo;
import com.cnsmash.pojo.vo.MyRankVo;
import com.cnsmash.pojo.vo.UserDetail;
import com.cnsmash.service.QuarterService;
import com.cnsmash.service.RankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author guanhuan_li
 */
@Slf4j
@Service
public class RankServiceImpl implements RankService {

    @Autowired
    UserRankMapper userRankMapper;

    @Autowired
    RankLogMapper rankLogMapper;

    @Autowired
    QuarterService quarterService;

    @Override
    public MyRankVo userRank(Long userId) {
        Quarter quarter = quarterService.getCurrent();
        String quarterCode = quarter.getCode();
        UserRank userRank = get(userId, quarterCode);
        MyRankVo vo = new MyRankVo(userId, quarterCode);
        if (userRank == null) {
            return vo;
        }
        BeanUtils.copyProperties(userRank, vo);
        return vo;
    }

    @Override
    public void submitRank(Long battleId, Long userId, BattleResultType type, Long change) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        // 对战新增积分都用对战id作为唯一标识
        String logKey = battleId.toString();
        RankChangeType changeType = convertType(type);
        if (check(userId, changeType, logKey)) {
            log.debug("积分不能重复添加,{}, {}, {}", userId, changeType, logKey);
            return;
        }

        Quarter quarter = quarterService.getCurrent();
        UserRank userRank = get(userId, quarter.getCode());
        if (userRank == null) {
            // 初始化赛季排位
            userRank = new UserRank();
            userRank.setUserId(userId);
            userRank.setQuarter(quarter.getCode());
            userRank.setRank(0L);
            userRank.setScore(1500L);
            userRank.setWin(0);
            userRank.setLost(0);
            userRank.setTotal(0);
            userRank.setCreateTime(now);
            userRank.setUpdateTime(now);
            userRank.setScore(userRank.getScore() + change);

            // 对局变化
            type.changeRank(userRank);
            userRankMapper.insert(userRank);
        } else {
            userRank.setScore(userRank.getScore() + change);
            // 对局变化
            type.changeRank(userRank);
            userRank.setUpdateTime(now);
            userRankMapper.updateById(userRank);
        }


        add(quarter.getCode(), userId, changeType, change, battleId.toString());
    }

    private RankChangeType convertType(BattleResultType type) {
        RankChangeType changeType;
        // 新增历史记录
        switch (type) {
            case win:
                changeType = RankChangeType.battle_win;
                break;
            case lose:
                changeType = RankChangeType.battle_lose;
                break;
            case tie:
            default:
                throw new CodeException(ErrorCode.NOT_SUPPORT_ENUM, "不支持的枚举");
        }
        return changeType;
    }

    @Override
    public void add(String quarter, Long userId, RankChangeType type, Long change, String logKey) {
        if (check(userId, type, logKey)) {
            log.debug("积分不能重复添加");
            return;
        }

        Timestamp now = new Timestamp(System.currentTimeMillis());
        UserRank userRank = get(userId, quarter);
        if (userRank == null) {
            // 初始化赛季排位
            userRank = new UserRank();
            userRank.setUserId(userId);
            userRank.setQuarter(quarter);
            userRank.setRank(0L);
            userRank.setScore(1500L);
            userRank.setWin(0);
            userRank.setTotal(0);
            userRank.setLost(0);
            userRank.setCreateTime(now);
            userRank.setUpdateTime(now);
            userRank.setScore(userRank.getScore() + change);
            // 对局变化
            userRankMapper.insert(userRank);
        } else {
            userRank.setScore(userRank.getScore() + change);
            // 对局变化
            userRank.setUpdateTime(now);
            userRankMapper.updateById(userRank);
        }

        RankLog log = new RankLog();
        log.setQuarter(quarter);
        log.setUserId(userId);
        log.setChangeScore(change);
        log.setChangeType(type.name());
        log.setLogKey(logKey);
        rankLogMapper.insert(log);
    }

    /**
     * 定义用户一种加分类型里面logkey不能重复
     * 例如：用户1 对战胜利类型 logkey定义成对战id（battleId） 不能重复加分
     * @param userId 用户id
     * @param changeType 变化类型
     * @param logKey 唯一标识
     * @return
     */
    private boolean check(Long userId, RankChangeType changeType, String logKey) {
        QueryWrapper<RankLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                .eq("change_type", changeType.name())
                .eq("log_key", logKey);
        return rankLogMapper.exists(queryWrapper);
    }

    @Override
    public Page<UserDetail> page(PageFighterRo ro) {
        return userRankMapper.pageFighter(new Page<>(ro.getCurrent(), ro.getSize()), ro);
    }

    @Override
    public UserRank initQuarterRank(Long userId, String quarter) {
        return null;
    }

    @Override
    public UserRank get(Long userId, String quarter) {
        QueryWrapper<UserRank> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                .eq("quarter",quarter);
        return userRankMapper.selectOne(queryWrapper);
    }
}
