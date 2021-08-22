package com.cnsmash.service;

import com.cnsmash.pojo.entity.Account;

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
}
