package com.cnsmash.pojo.vo;

import lombok.Data;

@Data
public class TournamentPlayerVo {

    private Long id;

    /**
     * 玩家ID
     */
    private Long playerId;

    /**
     * 玩家名称
     */
    private String nickName;

    /**
     * 玩家头像
     */
    private String avatar;

    /**
     * ban图信息
     */
    private String banMap;

    /**
     * 徽章资源
     */
    private String badgeUri;

    /**
     * 徽章说明
     */
    private String badgeNote;

}
