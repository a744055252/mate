package com.cnsmash.pojo.entity;

import com.cnsmash.pojo.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author guanhuan_li
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Account extends BaseEntity {

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

    private String password;

    private Integer sex;

    /**
     * 状态 0 正常 1 禁止
     */
    private Integer status;

    /** 关联第三方id */
    private Long mappingId;
}
