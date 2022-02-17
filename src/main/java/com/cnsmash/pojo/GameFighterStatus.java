package com.cnsmash.pojo;

import lombok.Getter;

/**
 * @author guanhuan_li
 * #date  2022/1/26 17:25
 */
@Getter
public enum GameFighterStatus {

    /** 比赛进行中 */
    ing,
    /** 提交了结果 */
    submit,
    /** 对局结束 */
    end,

}
