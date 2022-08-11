package com.cnsmash.pojo.vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ChallengerVo {

    private Long id;

    private Long userId;

    private String note;

    private String nickName;

    private String headSrc;

    private Timestamp startTime;

    private Timestamp endTime;

}
