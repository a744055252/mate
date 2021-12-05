package com.cnsmash.exception;

import lombok.Data;

/**
 * @author guanhuan_li
 */
public interface ErrorCode {

    int ACCOUNT_EXIT = 1001;

    int NICKNAME_EXIT = 1002;

    /** 赛季编码已存在 */
    int QUARTER_CODE_EXIT = 1003;

    int CREATE_FILE_ERROR = 1004;

    int SAVE_FILE_ERROR = 1005;

    int FILE_NAME_ERROR = 1006;

    int FILE_NULL_ERROR = 1007;

    int PASSWORD_ERROR = 1008;

    /** 当前不允许建房 */
    int CREATE_ROOM_ERROR = 1009;

    /** 当前不允许匹配 */
    int JOIN_ROOM_ERROR = 1010;

    /** 对战房间不存在 */
    int BATTLE_ROOM_ERROR = 1011;

    /** 对战分数不对 */
    int BATTLE_SCORE_ERROR = 1012;
}
