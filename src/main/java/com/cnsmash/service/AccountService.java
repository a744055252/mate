package com.cnsmash.service;

import com.cnsmash.pojo.entity.Account;
import com.cnsmash.pojo.entity.User;
import com.cnsmash.pojo.ro.RegisterUserRo;

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

}
