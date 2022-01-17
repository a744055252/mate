package com.cnsmash.pojo.bean;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Set;

/**
 * 房间
 * @author guanhuan_li
 */
@Data
public class Room {

    /** 建房人 */
    private Long createId;

    /** 房间号 */
    private String no;

    /** 密码 */
    private String pwd;

    /**
     * 服务器
     * 裸连zh 日服jp 港服hk 美服usa
     */
    private String server;

    /** 创建时间 */
    private Timestamp createTime;

}
