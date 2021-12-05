package com.cnsmash.pojo.bean;

import com.cnsmash.pojo.RoomRule;
import com.cnsmash.pojo.entity.User;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

/**
 * 房间
 * @author guanhuan_li
 */
@Data
public class Room {

    private Long id;

    /** 对战id */
    private Long battleId;

    /** 赛季 */
    private String quarter;

    /** 创建人 */
    private Long createId;

    /** 加入人 */
    private Long joinId;

    /** 创建人分数 */
    private Long createScore;

    /** 加入人分数 */
    private Long joinScore;

    /** 创建时间 */
    private Timestamp createTime;

    /** 房间号 */
    private String no;

    /** 密码 */
    private String pwd;

    /** 房间规则 */
    private RoomRule rule;

    /** 人数 */
    private Integer roomNum;

    /** 当前人数 */
    private Integer currentNum;

    /** 开始时间 */
    private Timestamp startTime;

    /**
     * 被ban图
     */
    private Set<String> banMap;

    /**
     * 创建人
     */
    private User createUser;

    /**
     * 加入人
     */
    private User joinUser;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(createId, room.createId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createId);
    }
}
