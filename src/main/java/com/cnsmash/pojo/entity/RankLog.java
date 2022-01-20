package com.cnsmash.pojo.entity;

import com.cnsmash.pojo.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author guanhuan_li
 * #date  2022/1/20 18:09
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RankLog extends BaseEntity {

    private Long id;

    /**
     * 用户id {@link User#getId()}
     */
    private Long userId;

    /**
     * 唯一标识 用来记录积分不重复添加
     */
    private String logKey;

    /**
     * 赛季
     */
    private String quarter;

    /**
     * 变化类型
     */
    private String changeType;

    /**
     * 变化量
     */
    private Long changeScore;

}
