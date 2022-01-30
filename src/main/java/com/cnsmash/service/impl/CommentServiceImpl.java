package com.cnsmash.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cnsmash.exception.CodeException;
import com.cnsmash.exception.ErrorCode;
import com.cnsmash.mapper.CommentMapper;
import com.cnsmash.pojo.CommentType;
import com.cnsmash.pojo.bean.PageRo;
import com.cnsmash.pojo.entity.Comment;
import com.cnsmash.pojo.ro.AddCommentRo;
import com.cnsmash.pojo.vo.CommentVo;
import com.cnsmash.pojo.vo.UserDetail;
import com.cnsmash.service.BattleService;
import com.cnsmash.service.CommentService;
import com.cnsmash.service.FileService;
import com.cnsmash.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
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

    @Autowired
    BattleService battleService;

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
        Map<Long, UserDetail> id2detail = new HashMap<>();
        if (userIds.size() != 0) {
            id2detail = userService.listUserDetail(userIds).stream().collect(Collectors.toMap(UserDetail::getId, Function.identity()));
        }

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

    @Override
    public void addComment(Long userId, AddCommentRo ro) {
        // 校验新增评论对象是否存在
        Long objectId = ro.getObjectId();
        switch (ro.getCommentType()) {
            case user:
                Optional.ofNullable(userService.getById(objectId))
                        .orElseThrow(()-> new CodeException(ErrorCode.COMMENT_ERROR, "用户不存在"));
                break;
            case battle:
                Optional.ofNullable(battleService.get(objectId))
                        .orElseThrow(()-> new CodeException(ErrorCode.COMMENT_ERROR, "对战不存在"));
                break;
            default:
                throw new CodeException(ErrorCode.NOT_SUPPORT_ENUM, "不支持的评论对象！");
        }

        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setObjectType(ro.getCommentType().name());
        comment.setObjectId(objectId);
        comment.setContent(ro.getContent());
        comment.setUpdateTime(now);
        comment.setCreateTime(now);
        commentMapper.insert(comment);
    }

    public Boolean getCanComment(Long author, Long player) {
        Long playedCount = battleService.getHead2HeadCount(author, player);
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("object_type", "user")
                .eq("object_id", player)
                .eq("user_id", author);
        List<Comment> commentList = commentMapper.selectList(queryWrapper);
        if (playedCount > commentList.size()) {
            return true;
        }
        return false;
    }
}
