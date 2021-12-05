package com.cnsmash.pojo.bean;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * 单打结果
 * @author guanhuan_li
 */
@Data
public class SingleBattleDetail {

    List<UserBattleDetail> userBattleDetails;

    /**
     * 单个用户对战详情
     */
    @Data
    public static class UserBattleDetail{
        /**
         * 参赛用户
         */
        private Long userId;

        /**
         * 参赛用户分数
         */
        private Long battleScore;

        /**
         * 参赛用户使用斗士
         */
        private Set<String> userFighterSet;
    }
}
