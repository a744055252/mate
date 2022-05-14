package com.cnsmash.pojo.ro;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TournamentSetFocusRo {

    /**
     * 对局ID
     */
    private Long tournamentSetId;

    /**
     * focus状态
     */
    private Integer focus;

}
