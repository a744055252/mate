package com.cnsmash.pojo.ro;

import com.cnsmash.pojo.CommentType;
import com.cnsmash.pojo.entity.Battle;
import com.cnsmash.pojo.entity.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author guanhuan_li
 */
@Data
public class AddCommentRo {

    /**
     * 评论的对象类型
     * 用户 user
     * 对战 battle
     */
    @NotNull
    private CommentType commentType;

    /**
     * 评论的对象类型
     * 用户 user {@link User#getId()}
     * 对战 battle {@link Battle#getId()}
     */
    @NotNull
    private Long objectId;

    /**
     * 内容
     */
    @NotBlank
    private String content;

}
