package com.cnsmash.pojo.vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TournamentResultVo {

    /**
     * 比赛ID
     */
    private Long tournamentId;

    /**
     * 比赛名称
     */
    private String name;

    /**
     * 比赛时间
     */
    private Timestamp time;

    /**
     * 选手成绩
     */
    private Integer result;

}
