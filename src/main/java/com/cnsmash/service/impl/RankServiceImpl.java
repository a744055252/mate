package com.cnsmash.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cnsmash.mapper.UserRankMapper;
import com.cnsmash.pojo.BattleResultType;
import com.cnsmash.pojo.entity.Quarter;
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
    public void submitRank(Long userId, BattleResultType type, Long change) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

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
