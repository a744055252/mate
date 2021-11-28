package com.cnsmash.pojo.entity;

import com.cnsmash.pojo.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 对战
 * @author guanhuan_li
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Battle extends BaseEntity {

    private Long id;

    /**
     * 赛季
     */
    private String quarter;

    /**
     * 对局类型
     * 单打、双打
     * type 是 single 单打 objectId存userId
     * type 是 doubles 双打 objectId存teamId
     */
    private String type;

    /**
     * 比赛次数 bio 1 3 5
     */
    private Integer gameNum;

    /**
     * 被ban图
     * List<String> 格式
     */
    private String banMap;

    /**
     * 获得本次对战胜利的参赛对象
     * objectId
     */
    private Long battleWin;

    /**
     * 对战结果详情 json格式
     * single: {@link com.cnsmash.pojo.bean.SingleBattleDetail}
     */
    private String detailJson;
}
