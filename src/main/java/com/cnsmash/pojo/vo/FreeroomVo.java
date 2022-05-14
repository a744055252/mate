package com.cnsmash.pojo.vo;

import com.cnsmash.pojo.FreeroomStatus;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author Toddhead
 */
@Data
public class FreeroomVo extends FreeroomThumbnailVo {

    /**
     * 房间密码
     */
    private String password;

    /**
     * 房间简述
     */
    private String description;

    /**
     * 创建时间
     */
    private Timestamp create_time;

    /**
     * 房间状态
     */
    private FreeroomStatus status;

}
