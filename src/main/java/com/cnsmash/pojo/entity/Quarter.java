package com.cnsmash.pojo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * 赛季
 * @author guanhuan_li
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Quarter {

    private Long id;

    /** 赛季 */
    private String name;

    /** 赛季编码 */
    private String code;

    /** 是否是当前赛季 */
    private Boolean current;

    /** 赛季开始时间 */
    private Timestamp beginTime;

    /** 赛季结束时间 */
    private Timestamp endTime;

    /** 赛季是否结算 */
    private Integer sumup;

}
