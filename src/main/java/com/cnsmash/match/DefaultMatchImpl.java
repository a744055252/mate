package com.cnsmash.match;

import com.cnsmash.pojo.bean.Room;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

/**
 * @author guanhuan_li
 */
public class DefaultMatchImpl implements MatchHandle {

    @Override
    public Optional<Room> match(MatchRo ro, Set<Room> roomList) {

        MatchGap matchGap = allowGap(ro);

        return roomList.stream()
                // 匹配的房间
                .filter(room -> match(ro, matchGap, room))
                // 分差最小的
                .min(Comparator.comparingLong((room) -> Math.abs(room.getCreateScore() - ro.getScore())));
    }

    private boolean match(MatchRo ro, MatchGap matchGap, Room room) {
        Long score = ro.getScore();
        return room.getCreateScore() >= (score - matchGap.getDownGap()) && room.getCreateScore() <= score + matchGap.getUpGap();
    }

    /**
     * 允许的分差
     * @param ro 匹配
     * @return 分差
     */
    private MatchGap allowGap(MatchRo ro) {

        long upGap = 100L;
        long downGap = 100L;

        // 基础分差
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        Timestamp findTime = ro.getFindTime();
        long waitSecond = (System.currentTimeMillis() - findTime.getTime()) / 1000;

        long wait = 0L;
        if (waitSecond < 30) {
            wait += 50L;
        } else if (waitSecond < 120) {
            wait += 100L;
        } else {
            wait += 150L;
        }
        upGap += wait;
        downGap += wait;
        return MatchGap.builder().upGap(upGap).downGap(downGap).build();
    }

}
