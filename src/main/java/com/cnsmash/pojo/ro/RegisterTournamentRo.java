package com.cnsmash.pojo.ro;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
public class RegisterTournamentRo {

    private Long id;

    /**
     * 比赛ID
     */
    private Long TournamentId;

    /**
     * ban图信息
     */
    private String banMap;

}
