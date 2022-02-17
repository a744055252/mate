package com.cnsmash.pojo.vo;

import com.cnsmash.pojo.UserBattleStatus;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author guanhuan_li
 */
@Data
public class UserBattleStatusVo {

    /** 用户匹配状态 */
    private UserBattleStatus battleStatus;

    /**
     * 对战id
     */
    private Long battleId;

    /** 开始排位时间 */
    private Timestamp findTime;
}
