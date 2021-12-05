package com.cnsmash.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cnsmash.mapper.UserMapper;
import com.cnsmash.pojo.entity.User;
import com.cnsmash.pojo.ro.UpdateMatchRuleRo;
import com.cnsmash.pojo.vo.UserDetail;
import com.cnsmash.service.UserService;
import com.cnsmash.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author guanhuan_li
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

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
        update(user);
    }
}
