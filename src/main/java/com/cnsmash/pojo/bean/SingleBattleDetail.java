package com.cnsmash.pojo.bean;

import com.cnsmash.pojo.BattleResultType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 单打结果
 * @author guanhuan_li
 */
@Data
public class SingleBattleDetail {

    Map<Long, UserBattleDetail> userId2detail;

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
         * 排位分数
         */
        private Long rankScore;

        /**
         * 对战结果
         */
        private BattleResultType type;

        /**
         * 当前用户提交的
         * 用户id和对应的比分
         * */
        private Map<Long, Long> id2score;

        /**
         * 参赛用户使用斗士
         */
        private Set<String> userFighterSet;
    }
}
