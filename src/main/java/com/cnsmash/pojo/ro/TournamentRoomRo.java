package com.cnsmash.pojo.ro;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TournamentRoomRo {

    /**
     * 对局ID
     */
    private Long tournamentSetId;

    /**
     * 房间信息
     */
    private String room;

}
