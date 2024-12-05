package com.upc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upc.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lww
 * @since 2024-07-20
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    @Select("SELECT c.*, u.username AS fromUsername " +
            "FROM comment c " +
            "LEFT JOIN user u ON c.fromUserId = u.userid " +
           "WHERE c.toUserId = #{toUserId}")
    List<Comment> testMutiple(Integer toUserId);

    @Select("SELECT COUNT(*) FROM comment")
    int getCommentNum();

    @Select("SELECT COUNT(*) FROM comment WHERE toUserId = #{userId}")
    Integer getCommentNumToMe(Integer userId);
}
