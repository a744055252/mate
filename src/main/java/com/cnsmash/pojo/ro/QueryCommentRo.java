package com.cnsmash.pojo.ro;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cnsmash.pojo.CommentType;
import com.cnsmash.pojo.entity.Battle;
import com.cnsmash.pojo.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author guanhuan_li
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryCommentRo {

    /**
     * 评论的对象类型
     * 用户 user
     * 对战 battle
     */
    private CommentType objectType;

    /**
     * 评论的对象类型
     * 用户 user {@link User#getId()}
     * 对战 battle {@link Battle#getId()}
     */
    private Long objectId;


}
