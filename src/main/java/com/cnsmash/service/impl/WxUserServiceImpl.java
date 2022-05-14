package com.cnsmash.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cnsmash.mapper.WxUserMapper;
import com.cnsmash.pojo.entity.WxUser;
import com.cnsmash.service.WxUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author guanhuan_li
 */
@Slf4j
@Service
public class WxUserServiceImpl implements WxUserService {

    @Autowired
    private WxUserMapper wxUserMapper;

    @Override
    public void save(WxUser wxUser) {
        wxUserMapper.insert(wxUser);
    }

    @Override
    public Optional<WxUser> getByOpenid(String openid) {
        QueryWrapper<WxUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);
        return Optional.ofNullable(wxUserMapper.selectOne(queryWrapper));
    }

    @Override
    public Optional<WxUser> get(Long wxUserId) {
        return Optional.ofNullable(wxUserMapper.selectById(wxUserId));
    }
}
