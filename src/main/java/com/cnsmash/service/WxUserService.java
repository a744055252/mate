package com.cnsmash.service;

import com.cnsmash.pojo.entity.WxUser;

import java.util.Optional;

/**
 * @author guanhuan_li
 */
public interface WxUserService {

    /**
     * 保存微信信息
     * @param wxUser 微信用户信息
     */
    void save(WxUser wxUser);

    /**
     * 获取微信用户数据
     * @param openid 微信用户id
     * @return 微信用户
     */
    Optional<WxUser> getByOpenid(String openid);

    /**
     * 获取微信用户
     * @param wxUserId 微信用户id
     * @return 微信信息
     */
    Optional<WxUser> get(Long wxUserId);
}
