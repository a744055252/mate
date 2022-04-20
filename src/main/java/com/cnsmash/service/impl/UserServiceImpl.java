package com.cnsmash.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cnsmash.mapper.BattleMapper;
import com.cnsmash.mapper.UserFighterMapper;
import com.cnsmash.mapper.UserMapper;
import com.cnsmash.pojo.BattleResultType;
import com.cnsmash.pojo.GameStatus;
import com.cnsmash.pojo.entity.*;
import com.cnsmash.pojo.ro.AddUserRo;
import com.cnsmash.pojo.ro.UpdateMatchRuleRo;
import com.cnsmash.pojo.vo.RuleVo;
import com.cnsmash.pojo.vo.UserDetail;
import com.cnsmash.service.QuarterService;
import com.cnsmash.service.RankService;
import com.cnsmash.service.UserService;
import com.cnsmash.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author guanhuan_li
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserFighterMapper userFighterMapper;

    @Autowired
    QuarterService quarterService;

    @Autowired
    RankService rankService;

    @Autowired
    BattleMapper battleMapper;

    @Override
    public User getById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public void add(AddUserRo ro) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        User user = new User();
        BeanUtils.copyProperties(ro, user);
        user.setTagJson(JsonUtil.toJson(ro.getTagList()));
        user.setAccountId(ro.getAccountId());
        user.setCode("");
        user.setTeamId(0L);
        user.setUpdateTime(now);
        user.setCreateTime(now);
        userMapper.insert(user);
    }

    @Override
    public List<UserDetail> listUserDetail(List<Long> userIds) {
        return userMapper.listUserDetail(userIds);
    }

    @Override
    public List<User> listByAccountId(Long accountId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", accountId);
        return userMapper.selectList(queryWrapper);
    }

    @Override
    public User getUserByNickName(String nickName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("nick_name", nickName);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public void add(User user) {
        userMapper.insert(user);
    }

    @Override
    public void update(User user) {
        userMapper.updateById(user);
    }

    @Override
    public void updateMatchRule(Long userId, UpdateMatchRuleRo ro) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        User user = getById(userId);
        user.setBanMap(JsonUtil.toJson(ro.getBanMap()));
        user.setUpdateTime(now);
        user.setServer(ro.getServer());
        user.setScoreGap(ro.getScoreGap());
        update(user);
    }

    @Override
    public void useFighter(String quarter, Long userId, BattleResultType type, Collection<String> fighterList) {
        Map<String, UserFighter> no2fighter = listAllUserFighter(userId).stream()
                .collect(Collectors.toMap(UserFighter::getFighterNo, Function.identity()));

        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        for (String fighter : fighterList) {
            UserFighter userFighter = no2fighter.get(fighter);
            if (userFighter == null) {
                userFighter = new UserFighter();
                userFighter.setUserId(userId);
                userFighter.setQuarter(quarter);
                userFighter.setFighterNo(fighter);
                userFighter.setCreateTime(now);
                userFighter.setLost(0);
                userFighter.setWin(0);
                userFighter.setTotal(0);
            }
            userFighter.setUpdateTime(now);
            type.changeFighter(userFighter);
            if (userFighter.getId() == null) {
                userFighterMapper.insert(userFighter);
            } else {
                userFighterMapper.updateById(userFighter);
            }
        }
    }

    public List<UserFighter> listAllUserFighter(Long userId) {
        QueryWrapper<UserFighter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return userFighterMapper.selectList(queryWrapper);
    }

    @Override
    public List<UserFighter> listUserFighter(Long userId) {
        QueryWrapper<UserFighter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
        .ge("total", 5);
        return userFighterMapper.selectList(queryWrapper);
    }

    @Override
    public RuleVo getMyRule(Long id) {
        User user = userMapper.selectById(id);
        RuleVo matchRuleRo = new RuleVo();
        BeanUtils.copyProperties(user, matchRuleRo);
        return matchRuleRo;
    }

    @Override
    public UserDetail getUserDetail(Long id) {
        UserDetail detail = new UserDetail();
        User user = getById(id);
        Quarter quarter = quarterService.getCurrent();
        BeanUtils.copyProperties(user, detail);
        // detail.setHeadSrc(fileService.findById(user.getHead()).getSrc());

        // 排位分
        UserRank userRank = rankService.get(user.getId(), quarter.getCode());
        if (userRank == null) {
            userRank = new UserRank();
            userRank.setUserId(user.getId());
            userRank.setQuarter(quarter.getCode());
            userRank.setRank(0L);
            userRank.setScore(1500L);
            userRank.setWin(0);
            userRank.setLost(0);
            userRank.setTotal(0);
        }
        detail.setUserRank(userRank);

        // 使用角色
        List<UserFighter> userFighters = listUserFighter(user.getId());
        detail.setUserFighterList(userFighters);
        return detail;
    }

    @Override
    public String getNickNameById(Long id) {
        return userMapper.getNickNameById(id);
    }

    @Override
    public List<String> getFighterById(Long id) {
        QueryWrapper<UserFighter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id)
                    .eq("quarter", quarterService.getCurrent().getCode())
                    .ge("total", 5)
                    .orderByDesc("total");
        List<UserFighter> userFighters = userFighterMapper.selectList(queryWrapper);
        List<String> fighters = new LinkedList<>();
        for (UserFighter userFighter : userFighters) {
            fighters.add(userFighter.getFighterNo());
        }
        return fighters;
    }

    @Override
    public void banUser(Long id) {
        User user = userMapper.selectById(id);
        List<Battle> battleList = battleMapper.getPlayerBattle(id, 10);
        int point = 0;
        for (int index = 9; index >= 0; index --) {
            Battle battle = battleList.get(index);
            if (GameStatus.end.name().equals(battle.getGameStatus())) {
                point -= 1;
                if (point < 0) {
                    point = 0;
                }
            }
            if (GameStatus.stop.name().equals(battle.getGameStatus())) {
                point += 2;
            }
        }
        LocalDateTime now = LocalDateTime.now();
        Integer banCount = user.getBanCount();

        if (point <= 2) {
            banCount = 0;
        } else if (point <= 10) {
            banCount += 1;
            if (banCount == 3) {
                user.setBanUntil(Timestamp.valueOf(now.plusMinutes(30)));
            }
        } else if (point <= 15) {
            banCount += 1;
            if (banCount == 3) {
                user.setBanUntil(Timestamp.valueOf(now.plusHours(2)));
            }
        } else {
            banCount += 1;
            if (banCount == 3) {
                user.setBanUntil(Timestamp.valueOf(now.plusHours(6)));
            }
        }
        user.setBanCount(banCount);
        userMapper.updateById(user);
    }
}
