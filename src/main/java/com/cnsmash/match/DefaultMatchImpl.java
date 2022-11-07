package com.cnsmash.match;

import org.apache.commons.lang3.time.DateUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
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
                // 同一个赛季
                //.filter(bean -> ro.getQuarter().equals(bean.getQuarter()))
                .filter(bean -> {
                    // 5分钟内不重复遇到同一个对手
                    Timestamp now = Timestamp.valueOf(LocalDateTime.now());
                    Date min5 = DateUtils.addMinutes(now, -5);
                    if (bean.getFindTime().compareTo(min5) > 0) {
                        return !bean.getLastUserIds().contains(ro.getUserId());
                    }
                    return true;
                })
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
