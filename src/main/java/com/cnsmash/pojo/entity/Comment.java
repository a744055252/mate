package com.cnsmash.pojo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author guanhuan_li
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Comment extends BaseEntity {

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
}
