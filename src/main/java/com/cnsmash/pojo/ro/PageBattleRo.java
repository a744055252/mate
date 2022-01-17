package com.cnsmash.pojo.ro;

import com.cnsmash.pojo.bean.PageRo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * 查询对战记录
 * @author guanhuan_li
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PageBattleRo extends PageRo {

    /** 用户id */
    private Long userId;

    /**
     * 赛季
     */
    private String quarter;

    /**
     * 对局类型
     * 单打、双打
     * type 是 single 单打 objectId存userId
     * type 是 doubles 双打 objectId存teamId
     */
    private String type;

    /**
     * 比赛状态
     * {@link com.cnsmash.pojo.GameStatus}
     */
    private String gameStatus;

    /**
     * 对战时间范围
     */
    private Timestamp beforeTime;

    /**
     * 对战时间范围
     */
    private Timestamp afterTime;
}
