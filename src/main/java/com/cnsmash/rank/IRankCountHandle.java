package com.cnsmash.rank;

import java.util.Map;

/**
 * 分数计算
 * @author guanhuan_li
 */
public interface IRankCountHandle {

    /**
     * 计算得分
     * @param id2BattleScore 用户id和比赛得分
     * @param id2RankScore 用户id和当前排位分数
     * @return 用户id和排名得分
     */
    Map<Long, Long> count(Map<Long, Long> id2BattleScore, Map<Long, Long> id2RankScore);

    /**
     * 计算方式
     * @return 方式
     */
    RankType rankType();

}
