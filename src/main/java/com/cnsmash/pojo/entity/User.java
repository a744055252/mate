package com.cnsmash.pojo.entity;

import com.cnsmash.pojo.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * @author guanhuan_li
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class User extends BaseEntity {

    private Long id;

    /**
     * 主账号id
     */
    private Long accountId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像 {@link UploadFile#getId()}
     */
    private Long head;

    private String code;

    /**
     * 队伍
     */
    private Long teamId;

    /**
     * 标签
     * List<String> json格式
     */
    private String tagJson;

    /**
     * 自我介绍
     */
    private String intro;

    /**
     * 链接方式
     * 1.有线 2.无线
     */
    private Integer linkType;

    /**
     * 被ban图
     * Set<String> 格式
     */
    private String banMap;

    /**
     * 服务器
     * 裸连1 港服2 日服4 字节流
     */
    private Integer server;

    /**
     * 分差
     */
    private Long scoreGap;

    /**
     * 上次建房时间
     * 比赛完会更新一次
     */
    private Timestamp createRoomTime;

    /**
     * 状态 0 正常 1 禁止
     */
    private Integer status;

    /**
     * 主号ID
     */
    private Long mainId;

    /**
     * 被ban时间
     */
    private Timestamp banUntil;
}
