package com.cnsmash.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cnsmash.mapper.SystemArgMapper;
import com.cnsmash.pojo.Arg;
import com.cnsmash.pojo.entity.SystemArg;
import com.cnsmash.pojo.entity.User;
import com.cnsmash.service.SystemArgService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统参数
 * @author guanhuan_li
 */
@Slf4j
@Service
public class SystemArgServiceImpl implements SystemArgService {

    @Autowired
    SystemArgMapper systemArgMapper;

    @Override
    public SystemArg get(Arg arg) {
        QueryWrapper<SystemArg> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("argKey", arg.name());
        return systemArgMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean allowCreateRoom() {
        SystemArg systemArg = get(Arg.createRoom);
        return Boolean.parseBoolean(systemArg.getArgValue());
    }

    @Override
    public boolean allowJoinRoom() {
        SystemArg systemArg = get(Arg.joinRoom);
        return Boolean.parseBoolean(systemArg.getArgValue());
    }
}
