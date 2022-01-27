package com.cnsmash.pojo.bean;

import com.cnsmash.pojo.CommentType;
import com.cnsmash.pojo.entity.Battle;
import com.cnsmash.pojo.entity.User;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author guanhuan_li
 */
@Data
public class PageRo {

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

    /** 当前页*/
    @NotNull
    @Min(0)
    private Long current;
    
    /** 页大小 */
    @Min(1)
    private Long size;

}
