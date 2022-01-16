package com.cnsmash.rank;

import com.cnsmash.exception.CodeException;
import com.cnsmash.exception.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * mate单打分数计算规则
 * @author guanhuan_li
 */
@Service
public class MateSingleRankCountHandleImpl implements IRankCountHandle {

    /** 参赛人数 */
    private static final int RANK_NUM = 2;

    /** 最大加分 */
    private static final long MAX_RANK = 30L;

    /** 最少加分 */
    private static final long MIN_RANK = 1L;

    @Override
    public Map<Long, Long> count(Map<Long, Long> id2score, Map<Long, Long> id2RankScore) {
        if (id2score.size() != RANK_NUM) {
            throw new CodeException(ErrorCode.RANK_SCORE_ERROR, "计算规则出错-人数不对");
        }
        Set<Map.Entry<Long, Long>> entries = id2score.entrySet();
        Iterator<Map.Entry<Long, Long>> iterator = entries.iterator();
        // 用户1
        Map.Entry<Long, Long> userScore1 = iterator.next();
        // 用户2
        Map.Entry<Long, Long> userScore2 = iterator.next();
        // 用户id
        Long userId1 = userScore1.getKey();
        Long userId2 = userScore2.getKey();
        // 对战分数
        Long userBattleScore1 = userScore1.getValue();
        Long userBattleScore2 = userScore2.getValue();
        if (userBattleScore1.equals(userBattleScore2)) {
            // 目前不支持平局
            throw new CodeException(ErrorCode.RANK_SCORE_ERROR, "计算规则出错-不支持平局");
        }

        // 当前排位分数
        Long userRankScore1 = id2RankScore.get(userId1);
        Long userRankScore2 = id2RankScore.get(userId2);


        // 计算分数交换
        Map<Long, Long> result = new HashMap<>(2);
        if (userBattleScore1.compareTo(userBattleScore2) > 0) {
            // 用户1赢
            Long rankScore = count(userRankScore1, userRankScore2);
            result.put(userId1, rankScore);
            result.put(userId2, -rankScore);
        } else {
            // 用户2赢
            Long rankScore = count(userRankScore2, userRankScore1);
            result.put(userId1, -rankScore);
            result.put(userId2, rankScore);
        }
        return result;
    }

    /**
     * 计算增减分数
     * @return 分数
     */
    public static Long count(Long winScore, Long loseScore) {
        double result;
        if(winScore >= loseScore) {
            result = Math.ceil(15 - (winScore.doubleValue() - loseScore.doubleValue())/26);
        } else {
            result = Math.floor(16 + (loseScore.doubleValue() - winScore.doubleValue())/24);
        }
        long realResult = (long) result;
        if (result > MAX_RANK) {
            realResult = MAX_RANK;
        }
        if (result < MIN_RANK) {
            realResult = MIN_RANK;
        }
        return realResult;
    }

    @Override
    public RankType rankType() {
        return RankType.mate_single;
    }
}
