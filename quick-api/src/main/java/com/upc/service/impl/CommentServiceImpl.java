package com.upc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upc.entity.Comment;
import com.upc.entity.User;
import com.upc.mapper.CommentMapper;
import com.upc.mapper.UserMapper;
import com.upc.service.ICommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lww
 * @since 2024-07-20
 */
@Service
public class CommentServiceImpl  implements ICommentService {

    private static final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Comment> testPage(Integer toUserId, Integer pageNum) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<Comment>();
        queryWrapper.eq(Comment::getToUserId, toUserId);
        Page<Comment> page = new Page<>(pageNum, 5);
        Page<Comment> commentPage= commentMapper.selectPage(page, queryWrapper);
        List<Comment> comments = commentPage.getRecords();
//        Set<Integer> userIds = comments.stream()
//                .flatMap(comment -> Stream.of(comment.getToUserId(),comment.getFromUserId()))
//                .filter(Objects::nonNull)
//                .collect(Collectors.toSet());
//        List<User> userList=userMapper.selectBatchIds(userIds);
        for (Comment comment : comments) {
            if (comment.getToUserId() != null) {
                User user1 = userMapper.selectById(comment.getToUserId());
                comment.setToUsername(user1.getUsername());
                comment.setToUserImage(user1.getImageUrl());
            }
            else {
                comment.setToUsername(" ");
                comment.setToUserImage(" ");
            }
            if (comment.getFromUserId()!=null) {
                User user1 = userMapper.selectById(comment.getFromUserId());
                comment.setFromUsername(user1.getUsername());
                comment.setFromUserImage(user1.getImageUrl());
            }
            else {
                comment.setFromUsername(" ");
                comment.setFromUserImage(" ");
            }
        }
        return comments;
    }

    @Override
    public List<Comment> getALLCommentsWithPage(Integer pageNum, Integer pageSize) {
        Page<Comment> page = new Page<>(pageNum, pageSize);
        Page<Comment> commentPage= commentMapper.selectPage(page, null);
        List<Comment> comments = commentPage.getRecords();
        for (Comment comment : comments) {
            if (comment.getToUserId() != null) {
                User user1 = userMapper.selectById(comment.getToUserId());
                comment.setToUsername(user1.getUsername());
                comment.setToUserImage(user1.getImageUrl());
                comment.setToUserPhone(user1.getPhone());
            }
            else {
                comment.setToUsername(" ");
                comment.setToUserImage(" ");
                comment.setToUserPhone(" ");
            }
            if (comment.getFromUserId()!=null) {
                User user1 = userMapper.selectById(comment.getFromUserId());
                comment.setFromUsername(user1.getUsername());
                comment.setFromUserImage(user1.getImageUrl());
                comment.setFromUserPhone(user1.getPhone());
            }
            else {
                comment.setFromUsername(" ");
                comment.setFromUserImage(" ");
                comment.setFromUserPhone(" ");
            }
        }
        return comments;
    }

    @Override
    public List<Comment> getCommentsFromMe(Integer pageNum, Integer pageSize, Integer fromUserId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getFromUserId, fromUserId);
        Page<Comment> page = new Page<>(pageNum, pageSize);
        Page<Comment> commentPage= commentMapper.selectPage(page, queryWrapper);
        List<Comment> comments = commentPage.getRecords();
        System.out.println(comments);
        for (Comment comment : comments) {
            if (comment.getToUserId() != null) {
                User user1 = userMapper.selectById(comment.getToUserId());
                comment.setToUsername(user1.getUsername());
                comment.setToUserImage(user1.getImageUrl());
            }
            else {
                comment.setToUsername(" ");
                comment.setToUserImage(" ");
            }
            if (comment.getFromUserId()!=null) {
                User user1 = userMapper.selectById(comment.getFromUserId());
                comment.setFromUsername(user1.getUsername());
                comment.setFromUserImage(user1.getImageUrl());
            }
            else {
                comment.setFromUsername(" ");
                comment.setFromUserImage(" ");
            }
        }
        return comments;
    }

    @Override
    public List<Comment> getCommentsToMe(Integer pageNum, Integer pageSize, Integer toUserId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getToUserId, toUserId);
        Page<Comment> page = new Page<>(pageNum, pageSize);
        Page<Comment> commentPage= commentMapper.selectPage(page, queryWrapper);
        List<Comment> comments = commentPage.getRecords();
        for (Comment comment : comments) {
            if (comment.getToUserId() != null) {
                User user1 = userMapper.selectById(comment.getToUserId());
                comment.setToUsername(user1.getUsername());
                comment.setToUserImage(user1.getImageUrl());
            }
            else {
                comment.setToUsername(" ");
                comment.setToUserImage(" ");
            }
            if (comment.getFromUserId()!=null) {
                User user1 = userMapper.selectById(comment.getFromUserId());
                comment.setFromUsername(user1.getUsername());
                comment.setFromUserImage(user1.getImageUrl());
            }
            else {
                comment.setFromUsername(" ");
                comment.setFromUserImage(" ");
            }
        }
        return comments;
    }

    @Override
    public int addComment(Comment comment) {
        int ok=-1;

        comment.setCreateTime(LocalDateTime.now());
        try {
            ok= commentMapper.insert(comment);
        } catch (Exception e) {
            return ok;
        }
        return ok;
    }

    @Override
    public int deleteComment(Integer id) {

           int ok=commentMapper.deleteById(id);

        return ok;
    }

    @Override
    public Comment getSpecificComment(Integer id) {
        Comment comment=commentMapper.selectById(id);
        if (comment==null) {
            return null;
        }
        if (comment.getToUserId() != null) {
            User user1 = userMapper.selectById(comment.getToUserId());
            comment.setToUsername(user1.getUsername());
            comment.setToUserImage(user1.getImageUrl());
        }
        else {
            comment.setToUsername(" ");
            comment.setToUserImage(" ");
        }
        if (comment.getFromUserId()!=null) {
            User user1 = userMapper.selectById(comment.getFromUserId());
            comment.setFromUsername(user1.getUsername());
            comment.setFromUserImage(user1.getImageUrl());
        }
        else {
            comment.setFromUsername(" ");
            comment.setFromUserImage(" ");
        }
        return comment;
    }
}
