package com.cnsmash.pojo.entity;

import com.cnsmash.pojo.TournamentStatus;
import com.cnsmash.pojo.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * @author Amaki
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Tournament extends BaseEntity {

    private Long id;

    /**
     * 比赛名称
     */
    private String name;

    /**
     * 报名开始时间
     */
    private Timestamp registerTime;

    /**
     * 比赛开始时间
     */
    private Timestamp startTime;

    /**
     * 比赛详情
     */
    private String detail;

    /**
     * 初始ban图
     */
    private String banStarter;

    /**
     * 初始ban图数量
     */
    private Integer banStarterCount;

    /**
     * 通用ban图
     */
    private String banCounter;

    /**
     * 通用ban图数量
     */
    private Integer banCounterCount;

    /**
     * 举办人ID
     */
    private Long host;

    /**
     * 直播地址
     */
    private String live;

    /**
     * 分数门槛下限
     */
    private Integer pointLow;

    /**
     * 分数门槛上限
     */
    private Integer pointHigh;

    /**
     * 人数上限
     */
    private Integer playerLimit;

    /**
     * 是否有线限定（0否 1是）
     */
    private Integer wire;

    /**
     * 限制使用的加速节点
     */
    private String server;

    /**
     * 比赛状态
     */
    private TournamentStatus status;

    /**
     * 有无第三名确定战
     */
    private Integer thirdPlace;
}
