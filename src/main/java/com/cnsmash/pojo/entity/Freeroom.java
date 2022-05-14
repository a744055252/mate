package com.cnsmash.pojo.entity;

import com.cnsmash.pojo.FreeroomStatus;
import com.cnsmash.pojo.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * @author Amaki
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Freeroom extends BaseEntity {

    private Long id;

    /**
     * 建房者ID
     */
    private Long host;

    /**
     * 房间ID
     */
    private String roomId;

    /**
     * 房间密码
     */
    private String password;

    /**
     * 人数上限
     */
    private Integer maximum;

    /**
     * 服务器
     */
    private String server;

    /**
     * 房间简述
     */
    private String description;

    /**
     * 过时时间
     */
    private Timestamp expireTime;

    /**
     * 房间状态
     */
    private FreeroomStatus status;

}
