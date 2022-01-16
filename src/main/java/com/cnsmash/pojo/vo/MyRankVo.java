package com.cnsmash.pojo.vo;

import com.cnsmash.pojo.entity.User;
import lombok.Data;

/**
 * @author guanhuan_li
 */
@Data
public class MyRankVo {

    public MyRankVo() {
    }

    public MyRankVo(Long userId, String quarter) {
        this.userId = userId;
        this.quarter = quarter;
        this.lost = 0;
        this.win = 0;
        this.rank = 0L;
        this.total = 0;
        this.score = 1500L;
    }

    /**
     * 用户id {@link User#getId()}
     */
    private Long userId;

    /**
     * 赛季
     */
    private String quarter;

    /**
     * 排名
     */
    private Long rank;

    /**
     * 排名分数
     */
    private Long score;

    /**
     * 胜局
     */
    private Integer win;

    /**
     * 败局
     */
    private Integer lost;

    /**
     * 总对局
     */
    private Integer total;

}
