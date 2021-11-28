package com.cnsmash.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cnsmash.pojo.CommentType;
import com.cnsmash.pojo.bean.PageRo;
import com.cnsmash.pojo.entity.Comment;
import com.cnsmash.pojo.vo.CommentVo;

import java.util.List;

/**
 * @author guanhuan_li
 */
public interface CommentService {

    /**
     * 获取对象的评论
     * @param type 对象类型
     * @param objectId 对象id
     * @return 评论列表
     */
    List<Comment> listByObject(CommentType type, Long objectId);

    /**
     * 获取对象的评论
     * @param type 对象类型
     * @param objectId 对象id
     * @param ro 分页
     * @return 评论列表
     */
    Page<CommentVo> page(CommentType type, Long objectId, PageRo ro);
}
