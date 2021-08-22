package com.cnsmash.config.login.service;

import com.cnsmash.config.login.pojo.LoginType;
import com.cnsmash.config.login.pojo.LoginUser;
import com.cnsmash.config.login.pojo.LoginUserRo;
import com.cnsmash.config.login.pojo.LoginUserVo;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author lgh
 */
public interface LoginUserService{

    /**
     * 获取登录用户信息
     * @param loginUser 登录用户
     * @return 登录用户信息
     */
    LoginUserVo getLoginUserVo(LoginUser loginUser);

    /**
     * 获取登录类型
     * @return 登录类型
     */
    LoginType getLoginType();

    /**
     * 获取加密方法
     * @return 加密方法
     */
    PasswordEncoder getPasswordEncoder();

    /**
     * 登录
     * @param ro 请求参数
     * @return 登录用户
     */
    LoginUser loadUser(LoginUserRo ro);
}
