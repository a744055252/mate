package com.cnsmash.pojo.vo;

import com.cnsmash.pojo.entity.Account;
import com.cnsmash.pojo.entity.User;
import lombok.Data;

import java.util.List;

/**
 * @author guanhuan_li
 */
@Data
public class AccountUserVo {

    /** 账号信息 */
    private Account account;

    /** 登录凭证 */
    private String auth;

    /** 身份列表 */
    private List<User> userList;

}
