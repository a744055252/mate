package com.cnsmash.pojo.vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TournamentThumbnailVo {

    private Long id;

    /**
     * 比赛名称
     */
    private String name;

    /**
     * 详细
     */
    private String detail;

    /**
     * 比赛开始时间
     */
    private Timestamp startTime;

    /**
     * 举办人ID
     */
    private Long host;

    /**
     * 举办人TAG
     */
    private String hostName;

    /**
     * 举办人头像
     */
    private String hostHead;

}
