package com.upc.service;

import com.upc.entity.Comment;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lww
 * @since 2024-07-20
 */
public interface ICommentService  {

    List<Comment> testPage(Integer id, Integer pageNum);

    List<Comment> getALLCommentsWithPage(Integer pageNum, Integer pageSize);

    List<Comment> getCommentsFromMe(Integer pageNum, Integer pageSize, Integer fromUserId);

    List<Comment> getCommentsToMe(Integer pageNum, Integer pageSize, Integer toUserId);

    int addComment(Comment comment);

    int deleteComment(Integer id);

    Comment getSpecificComment(Integer id);

//    List<Comment> testMutip(Integer toUserId);
}
