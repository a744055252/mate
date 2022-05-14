package com.cnsmash.pojo.ro;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
public class AddTournamentRo {

    private Long id;

    /**
     * 比赛名称
     */
    @NotNull
    private String name;

    /**
     * 报名开始时间
     */
    @NotNull
    private Timestamp registerTime;

    /**
     * 比赛开始时间
     */
    @NotNull
    private Timestamp startTime;

    /**
     * 比赛详情
     */
    @NotNull
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
    @NotNull
    private Integer wire;

    /**
     * 限制使用的加速节点
     */
    private String server;

}
