package com.cnsmash.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author guanhuan_li
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Account extends BaseEntity{
    @TableId(type = IdType.AUTO)
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

}
