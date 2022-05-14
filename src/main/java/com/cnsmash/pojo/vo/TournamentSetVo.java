package com.cnsmash.pojo.vo;

import lombok.Data;

@Data
public class TournamentSetVo {

    private Long id;

    /**
     * 对局序号
     */
    private Integer serial;

    /**
     * 玩家1ID
     */
    private Long player1Id;

    /**
     * 玩家1名字
     */
    private String player1Nickname;

    /**
     * 玩家2ID
     */
    private Long player2Id;

    /**
     * 玩家2名字
     */
    private String player2Nickname;

    /**
     * 胜者ID
     */
    private Long winnerId;

    /**
     * 是否被管理员标记
     */
    private Integer focus;

    /**
     * 玩家1ban图
     */
    private String player1BanMap;

    /**
     * 玩家2ban图
     */
    private String player2BanMap;

    /**
     * 房间信息
     */
    private String room;

}
