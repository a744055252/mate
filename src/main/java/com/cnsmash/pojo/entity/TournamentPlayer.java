package com.cnsmash.pojo.entity;

import com.cnsmash.pojo.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Amaki
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TournamentPlayer extends BaseEntity {

    private Long id;

    /**
     * 比赛ID
     */
    private Long tournamentId;

    /**
     * 玩家ID
     */
    private Long playerId;

    /**
     * ban图数据
     */
    private String banMap;

    /**
     * 种子位
     */
    private Integer seed;

    /**
     * 最终排名
     */
    private Integer result;

}
