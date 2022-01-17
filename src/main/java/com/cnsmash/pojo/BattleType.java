package com.cnsmash.pojo;

import lombok.Getter;

/**
 * @author guanhuan_li
 */
@Getter
public enum BattleType {
    /** 对战类型 */
    single(2),
    doubles(4),
    ;

    /**
     * 参赛人数
     */
    private int num;

    BattleType(int num) {
        this.num = num;
    }

}
