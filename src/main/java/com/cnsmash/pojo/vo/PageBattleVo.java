package com.cnsmash.pojo.vo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author guanhuan_li
 */
@Data
public class PageBattleVo {

    private Long id;

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
     * 比赛次数 bio 1 3 5
     */
    private Integer gameNum;

    /**
     * 被ban图
     * Set<String> 格式
     */
    private String banMap;

    /**
     * 获得本次对战胜利的参赛对象
     * objectId
     */
    private Long battleWin;

    /**
     * 比赛状态
     * {@link com.cnsmash.pojo.GameStatus}
     */
    private String gameStatus;

    /**
     * 对战结果详情 json格式
     * single: {@link com.cnsmash.pojo.bean.SingleBattleDetail}
     */
    private String detailJson;

    /**
     * 结果提交人
     */
    private Long commitId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 对战结束时间
     */
    private Timestamp endTime;

}
