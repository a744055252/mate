package com.cnsmash.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cnsmash.mapper.UserFighterMapper;
import com.cnsmash.mapper.UserMapper;
import com.cnsmash.pojo.BattleResultType;
import com.cnsmash.pojo.entity.User;
import com.cnsmash.pojo.entity.UserFighter;
import com.cnsmash.pojo.ro.UpdateMatchRuleRo;
import com.cnsmash.pojo.vo.UserDetail;
import com.cnsmash.service.UserService;
import com.cnsmash.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
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

    @Override
    public User getById(Long id) {
        return userMapper.selectById(id);
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
    public void useFighter(Long userId, BattleResultType type, Collection<String> fighterList) {
        Map<String, UserFighter> no2fighter = listAllUserFighter(userId).stream()
                .collect(Collectors.toMap(UserFighter::getFighterNo, Function.identity()));

        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        for (String fighter : fighterList) {
            UserFighter userFighter = no2fighter.get(fighter);
            if (userFighter == null) {
                userFighter = new UserFighter();
                userFighter.setUserId(userId);
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
}
