package com.cnsmash.pojo.bean;

import lombok.Data;

import java.util.List;

/**
 * 单打结果
 * @author guanhuan_li
 */
@Data
public class SingleBattleDetail {

    /**
     * 参赛用户1
     */
    private Long userId1;

    /**
     * 参赛用户2
     */
    private Long userId2;

    /**
     * 参赛用户1分数
     */
    private Long battleScore1;

    /**
     * 参赛用户1分数
     */
    private Long battleScore2;

    /**
     * 参赛用户1使用斗士
     */
    private List<String> user1FighterList;

    /**
     * 参赛用户1使用斗士
     */
    private List<String> user2FighterList;
}
