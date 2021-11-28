package com.cnsmash.service;

import com.cnsmash.config.login.pojo.LoginUser;
import com.cnsmash.pojo.entity.Account;
import com.cnsmash.pojo.entity.User;
import com.cnsmash.pojo.ro.RegisterUserRo;
import com.cnsmash.pojo.ro.UpdatePasswordRo;
import com.cnsmash.pojo.ro.UpdateUserInfoRo;
import com.cnsmash.pojo.vo.UserInfo;

import java.util.List;

/**
 * @author guanhuan_li
 */
public interface AccountService {

    /**
     * 登录
     * @param username 账号
     * @return 账号
     */
    Account login(String username);

    /**
     * 注册
     * @param ro 请求
     * @return 注册用户
     */
    User register(RegisterUserRo ro);


    /**
     * 获取用户信息
     * @param accountId 账号id
     * @return 用户信息
     */
    UserInfo info(Long accountId);

    /**
     * 更新用户密码
     * @param ro 更新
     */
    void updatePassword(LoginUser loginUser, UpdatePasswordRo ro);

    /**
     * 更新用户信息
     * @param loginUser 登录用户
     * @param ro 更新值
     */
    void updateUserInfo(LoginUser loginUser, UpdateUserInfoRo ro);
}
