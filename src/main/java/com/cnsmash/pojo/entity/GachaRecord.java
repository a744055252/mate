package com.cnsmash.pojo.entity;

import com.cnsmash.pojo.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Toddhead
 * 抽奖记录
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GachaRecord extends BaseEntity {

    private Long id;

    private Long userId;

    private Integer gachaType;

    private String gachaResult;

}
