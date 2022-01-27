package com.cnsmash.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cnsmash.config.login.pojo.LoginUser;
import com.cnsmash.pojo.CommentType;
import com.cnsmash.pojo.bean.PageRo;
import com.cnsmash.pojo.bean.ReposResult;
import com.cnsmash.pojo.ro.AddCommentRo;
import com.cnsmash.pojo.vo.CommentVo;
import com.cnsmash.service.CommentService;
import com.cnsmash.util.MateAuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author guanhuan_li
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping
    public ReposResult<Page<CommentVo>> myComment(@Valid PageRo ro){
        return ReposResult.ok(commentService.page(ro.getCommentType(), ro.getObjectId(), ro));
    }

    @PostMapping
    public ReposResult<Void> addComment(@Valid @RequestBody AddCommentRo ro) {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        commentService.addComment(loginUser.getUserId(), ro);
        return ReposResult.ok();
    }

}
