package com.cnsmash.pojo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 对战
 * @author guanhuan_li
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Battle extends BaseEntity{

    private Long id;

    /**
     * 赛季
     */
    private String quarter;

    /**
     * 对局类型
     * bio1 bio3 bio5 等
     */
    private String type;

    /**
     * 被ban图
     * List<String> 格式
     */
    private String banMap;



}
