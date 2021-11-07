package com.cnsmash.pojo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 一次对战的一局
 * @author guanhuan_li
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class BattleGame extends BaseEntity{

    private Long id;

    /**
     * 对战的id {@link Battle#getId()}
     */
    private Long battleId;

    /**
     * 第几局
     */
    private Integer sort;

    /**
     * 对局类型
     * 单打、双打
     * type 是 single 单打 objectId存userId
     * type 是 doubles 双打 objectId存teamId
     */
    private String type;

    /**
     * 选择地图
     */
    private String map;

    /**
     * 被ban图
     * List<String> 格式
     */
    private String banMap;

    /**
     * 获得本局胜利的参数对象
     * objectId
     */
    private Long gameWinId;
}
