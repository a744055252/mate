package com.cnsmash.pojo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author guanhuan_li
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Account extends BaseEntity{

    private Long id;

    private String name;

    private String phone;

    private String mail;

    private String account;

    private String password;
}
