package com.cnsmash.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cnsmash.mapper.CommentMapper;
import com.cnsmash.pojo.CommentType;
import com.cnsmash.pojo.bean.PageRo;
import com.cnsmash.pojo.entity.Comment;
import com.cnsmash.pojo.vo.CommentVo;
import com.cnsmash.pojo.vo.UserDetail;
import com.cnsmash.service.CommentService;
import com.cnsmash.service.FileService;
import com.cnsmash.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author guanhuan_li
 */
@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UserService userService;

    @Autowired
    FileService fileService;

    @Override
    public List<Comment> listByObject(CommentType type, Long objectId) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("objectType", type.name())
                .eq("objectId", objectId);
        return commentMapper.selectList(queryWrapper);
    }

    @Override
    public Page<CommentVo> page(CommentType type, Long objectId, PageRo ro) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("object_type", type.name())
                .eq("object_id", objectId);
        Page<Comment> page = commentMapper.selectPage(new Page<>(ro.getCurrent(), ro.getSize()), queryWrapper);

        Page<CommentVo> result = new Page<>();
        BeanUtils.copyProperties(page, result);

        List<Long> userIds = page.getRecords().stream().map(Comment::getUserId).collect(Collectors.toList());
        Map<Long, UserDetail> id2detail = userService.listUserDetail(userIds).stream().collect(Collectors.toMap(UserDetail::getId, Function.identity()));

        List<CommentVo> vos = new ArrayList<>();
        for (Comment comment : page.getRecords()) {
            CommentVo vo = new CommentVo();
            BeanUtils.copyProperties(comment, vo);

            UserDetail userDetail = id2detail.get(comment.getUserId());
            vo.setUserDetail(userDetail);
            vos.add(vo);
        }

        result.setRecords(vos);
        return result;
    }
}
