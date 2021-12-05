package com.cnsmash.match;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author guanhuan_li
 */
@Data
@Builder
public class MatchRo {

    /** 用户id */
    private Long userId;

    /** 分数 */
    private Long score;

    /** 开始排位时间 */
    private Timestamp findTime;


}
