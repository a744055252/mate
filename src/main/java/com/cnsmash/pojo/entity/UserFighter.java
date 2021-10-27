package com.cnsmash.pojo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户使用角色
 * @author guanhuan_li
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserFighter extends BaseEntity {

    private Long id;

    /**
     * 用户id {@link User#getId()}
     */
    private Long userId;

    /**
     * 斗士编码
     */
    private String fighterNo;

    /**
     * 斗士名
     */
    private String fighterName;

    /**
     * 胜局
     */
    private Integer win;

    /**
     * 败局
     */
    private Integer lost;

    /**
     * 使用次数
     */
    private Integer total;
}
