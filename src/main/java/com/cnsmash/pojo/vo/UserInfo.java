package com.cnsmash.pojo.vo;

import lombok.Data;

/**
 * @author guanhuan_li
 */
@Data
public class UserInfo {

    private Long id;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    private String mail;

    private String account;

    private Integer sex;

    private UserDetail user;

}
