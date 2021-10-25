package com.cnsmash.pojo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author guanhuan_li
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class User extends BaseEntity{

    private Long id;

    private String name;

    private Integer sex;

    private String code;

    /**
     * 队伍
     */
    private String team;

    /**
     * 标签
     */
    private String tag;

    /**
     * 自我介绍
     */
    private String desc;

    /**
     * 链接方式
     * 1.有线 2.无线
     */
    private Integer linkType;

}
