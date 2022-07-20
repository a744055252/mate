package com.cnsmash.pojo;

import lombok.Getter;

/**
 * @author Toddhead
 */
@Getter
public enum TournamentStatus {
    /** 比赛状态 */
    before, // 可以报名
    pending, // 报名截止
    process,
    finish,
    cancel
    ;
}
