package com.cnsmash.pojo;

import lombok.Getter;

/**
 * @author Toddhead
 */
@Getter
public enum TournamentStatus {
    /** 比赛状态 */
    before,
    process,
    finish,
    cancel
    ;
}
