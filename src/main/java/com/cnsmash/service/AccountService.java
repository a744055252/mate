package com.cnsmash.service;

import com.cnsmash.config.login.pojo.LoginUser;
import com.cnsmash.pojo.entity.Account;
import com.cnsmash.pojo.entity.User;
import com.cnsmash.pojo.ro.AccountUserRo;
import com.cnsmash.pojo.ro.RegisterUserRo;
import com.cnsmash.pojo.ro.UpdatePasswordRo;
import com.cnsmash.pojo.ro.UpdateUserInfoRo;
import com.cnsmash.pojo.vo.AccountUserVo;
import com.cnsmash.pojo.vo.UserInfo;

import java.util.List;

/**
 * @author guanhuan_li
 */
public interface AccountService {

    String LOGIN_AUTH_KEY = "login_auth";

    /**
     * 登录
     * @param username 账号
     * @return 账号
     */
    Account login(String username);

    /**
     * 获取用户身份列表
     * @param ro 用户凭证信息
     * @return 登录信息
     */
    AccountUserVo user(AccountUserRo ro);

    /**
     * 微信获取用户身份列表
     * @param code 微信code
     * @return 登录信息
     */
    AccountUserVo wxUser(String code);

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
     * @param loginUser 登录用户
     */
    void updatePassword(LoginUser loginUser, UpdatePasswordRo ro);

    /**
     * 更新用户信息
     * @param loginUser 登录用户
     * @param ro 更新值
     */
    void updateUserInfo(LoginUser loginUser, UpdateUserInfoRo ro);

    /**
     * 获取账号信息
     * @param accountId 账号id
     * @return 账号
     */
    Account get(Long accountId);

    /**
     * 重新登录获取auth
     * @param accountId 账号id
     * @return 账号信息
     */
    AccountUserVo switchUser(Long accountId);

    /**
     * 更新账号
     * @param account 账号
     */
    void update(Account account);
}
