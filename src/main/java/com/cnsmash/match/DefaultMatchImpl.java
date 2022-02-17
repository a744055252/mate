package com.cnsmash.match;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

/**
 * @author guanhuan_li
 */
public class DefaultMatchImpl implements MatchHandle {

    @Override
    public Optional<MatchBean> match(MatchBean ro, Map<Long, MatchBean> waitMatchMap) {
        return waitMatchMap.values().stream()
                // 自己的跳过
                .filter(bean-> !ro.getUserId().equals(bean.getUserId()))
                // 匹配的房间
                .filter(bean -> match(ro, bean))
                // 分差最小的
                .min(Comparator.comparingLong((bean) -> Math.abs(bean.getScore() - ro.getScore())));
    }

    private boolean match(MatchBean ro, MatchBean bean) {
        long gap = Math.abs(ro.getScore() - bean.getScore());
        return gap < ro.getScoreGap() && gap < bean.getScoreGap() && ((ro.getServer() & bean.getServer()) != 0);
    }

}
