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

    public String serverConvertToZh(){
        String result = "";
        switch (server){
            case "zh":
                result = "裸连";
                break;
            case "jp":
                result = "日服";
                break;
            case "hk":
                result = "港服";
                break;
            case "usa":
                result = "美服";
                break;
            default:
        }
        return result;
    }
}
