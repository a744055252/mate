package com.cnsmash.pojo.vo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author Toddhead
 */
@Data
public class FreeroomThumbnailVo {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 房间ID
     */
    private String roomId;

    /**
     * 人数限制
     */
    private Integer maximum;

    /**
     * 服务器
     */
    private String server;

    /**
     * 更新时间
     */
    private Timestamp updateTime;

    /**
     * 创建者ID
     */
    private Long hostId;

    /**
     * 创建者头像
     */
    private String hostAvatar;

    /**
     * 创建者名字
     */
    private String hostName;

}
