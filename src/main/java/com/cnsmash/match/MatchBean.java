package com.cnsmash.match;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

/**
 * @author guanhuan_li
 */
@Data
@Builder
public class MatchBean {

    /** 用户id */
    private Long userId;

    /** 分数 */
    private Long score;

    /** 赛季 */
    private String quarter;

    /**
     * 服务器
     * 裸连zh 日服jp 港服hk 美服usa
     */
    private Integer server;

    /**
     * 分差
     */
    private Long scoreGap;

    /**
     * 上次比赛的用户
     */
    private Set<Long> lastUserIds;

    /** 开始排位时间 */
    private Timestamp findTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MatchBean matchBean = (MatchBean) o;
        return userId.equals(matchBean.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
