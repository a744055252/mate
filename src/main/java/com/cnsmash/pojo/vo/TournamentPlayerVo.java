package com.cnsmash.pojo.vo;

import lombok.Data;

@Data
public class TournamentPlayerVo {

    private Long id;

    /**
     * 玩家ID
     */
    private Long plyaerId;

    /**
     * 玩家名称
     */
    private String nickname;

    /**
     * 玩家头像
     */
    private String avatar;

    /**
     * ban图信息
     */
    private String banMap;

}
