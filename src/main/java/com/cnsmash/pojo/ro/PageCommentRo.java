package com.cnsmash.pojo.ro;

import com.cnsmash.pojo.CommentType;
import com.cnsmash.pojo.bean.PageRo;
import com.cnsmash.pojo.entity.Battle;
import com.cnsmash.pojo.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * 查询评论记录
 * @author Toddhead
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PageCommentRo extends PageRo {

    /**
     * 评论的对象类型
     * 用户 user
     * 对战 battle
     */
    @NotNull
    private CommentType commentType;

    /**
     * 评论的对象ID
     * 用户 user {@link User#getId()}
     * 对战 battle {@link Battle#getId()}
     */
    @NotNull
    private Long objectId;

    /**
     * 查询起始ID
     */
    private Long latestId;

}
