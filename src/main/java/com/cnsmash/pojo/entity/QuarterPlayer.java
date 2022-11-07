package com.cnsmash.pojo.entity;

import com.cnsmash.pojo.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义赛季报名
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QuarterPlayer extends BaseEntity {

    private Long id;

    /** 玩家ID */
    private Long playerId;

    /** 赛季ID */
    private Long quarterId;

    /** ban图 */
    private String banMap;

}
