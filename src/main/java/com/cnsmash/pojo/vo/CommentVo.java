package com.cnsmash.pojo.vo;

import com.cnsmash.pojo.entity.Battle;
import com.cnsmash.pojo.entity.User;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author guanhuan_li
 */
@Data
public class CommentVo {

    private Long id;

    /**
     * 评论的对象类型
     * 用户 user
     * 对战 battle
     */
    private String objectType;

    /**
     * 评论的对象类型
     * 用户 user {@link User#getId()}
     * 对战 battle {@link Battle#getId()}
     */
    private Long objectId;

    /**
     * 内容
     */
    private String content;

    /**
     * 评论人
     */
    private Long userId;

    private UserDetail userDetail;

    private Timestamp createTime;

    private Timestamp updateTime;


}
