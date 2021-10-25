package com.cnsmash.pojo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * @author guanhuan
 * 徽章
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Badge extends BaseEntity {

    private Long id;

    /**
     * 用户id
     * {@link User#getId()}
     */
    private Long userId;

    /**
     * 编号
     */
    private Integer no;

    /**
     * 名称
     */
    private String name;

    /**
     * 获得时间
     */
    private Timestamp obtainTime;

    /**
     * 获取方式
     */
    private String obtainWay;
}
