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
    int MATCH_ALLOW_ERROR = 1010;

    /** 对战房间不存在 */
    int BATTLE_ROOM_ERROR = 1011;

    /** 对战分数不对 */
    int BATTLE_SCORE_ERROR = 1012;
    
    /** 分数计算出错 */
    int RANK_SCORE_ERROR = 1013;

    /** 不支持的枚举 */
    int NOT_SUPPORT_ENUM = 1014;

    /** 参赛人数有误 */
    int GAME_NUM_ERROR = 1015;

    /** 对战不存在 */
    int BATTLE_ERROR = 1016;

    /** 评论出错 */
    int COMMENT_ERROR = 1017;

    /** 微信接口调用错误，请稍后再试 */
    int WECHAT_ERROR = 1018;
}
