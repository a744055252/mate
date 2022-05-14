package com.cnsmash.pojo.ro;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TournamentReportRo {

    /**
     * 比赛ID
     */
    private Long tournamentId;

    /**
     * 对局ID
     */
    private Long tournamentSetId;

    /**
     * 比赛序号
     */
    private Integer serial;

}
