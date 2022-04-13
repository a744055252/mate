package com.cnsmash.pojo.entity;

import com.cnsmash.pojo.TournamentSetStatus;
import com.cnsmash.pojo.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Amaki
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TournamentSet extends BaseEntity {

    private Long id;

    /**
     * 比赛ID
     */
    private Long tournamentId;

    /**
     * 对局序号
     */
    private Integer serial;

    /**
     * 玩家1ID
     */
    private Long player1Id;

    /**
     * 玩家2ID
     */
    private Long player2Id;

    /**
     * 胜者ID
     */
    private Long winnerId;

    /**
     * 对局状态
     */
    private TournamentSetStatus status;

    /**
     * 房间信息
     */
    private String room;

    /**
     * 是否被管理员标记
     */
    private Integer focus;
}
