package com.upc.controller.user;


import com.upc.common.Result;
import com.upc.entity.Comment;
import com.upc.entity.User;
import com.upc.mapper.CommentMapper;
import com.upc.mapper.UserMapper;
import com.upc.service.ICommentService;
import com.upc.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lww
 * @since 2024-07-20
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    private static final Logger log = LoggerFactory.getLogger(CommentController.class);
    @Autowired
    ICommentService commentService;
    @Autowired
    CommentMapper commentMapper;




    @GetMapping("/testPage")
    public Result testPageSelect(Integer pageNum)
    {
        Integer toUserId=5;
        List<Comment> commentList=commentService.testPage(toUserId,pageNum);
        System.out.println(commentList);
        return Result.success(commentList);

    }

    //分页获取所有的comments
    @GetMapping("/getAllCommentsWithPage")
    public Result getAllCommentsWithPage(Integer pageNum,Integer pageSize)
    {

        List<Comment> commentList=commentService.getALLCommentsWithPage(pageNum,pageSize);
        return Result.success(commentList);
    }
    //分页获取我评价过的comments

    @GetMapping("/getCommentsFromMe")
    public Result getCommentsFromMe(Integer pageNum,Integer pageSize,@RequestHeader("token") String jwt)
    {
        Map<String,Object> chaims= JwtUtils.parseJWT(jwt);
        Integer fromUserId=(Integer) chaims.get("userid");
        List<Comment> commentList=commentService.getCommentsFromMe(pageNum,pageSize,fromUserId);
        return Result.success(commentList);
    }

    @GetMapping("/getSpecificComment")
    public Result getSpecificComment(Integer id)
    {
        Comment comment=commentService.getSpecificComment(id);
        if (comment==null)
        {
            return Result.error("查询失败");
        }
        return Result.success(comment);
    }


    @GetMapping("/getCommentsToMe")
    public Result getCommentsToMe(Integer pageNum,Integer pageSize,@RequestHeader("token") String jwt)
    {
        Map<String,Object> chaims= JwtUtils.parseJWT(jwt);
        Integer toUserId=(Integer) chaims.get("userid");
        List<Comment> commentList=commentService.getCommentsToMe(pageNum,pageSize,toUserId);
        return  Result.success(commentList);
    }


    @PostMapping("/addComment")
    public Result addComment(@RequestBody Comment comment)  //完成订单之后进行评价，fromid和toid，就是publishid和acceptid
    {
        int ok=commentService.addComment(comment);
        log.info(String.valueOf(ok));
        if(ok==-1)
        {
            return Result.error("添加失败");
        }
        return Result.success();
    }

    @GetMapping("/deleteComment")
    public Result deleteComment(Integer id)
    {
        int ok=commentService.deleteComment(id);
        if (ok==0)
        {
            return Result.error("删除失败");
        }
        return Result.success();
    }
    @GetMapping("getCommentNum")
    public Result getCommentNum()
    {
        int num=commentMapper.getCommentNum();
        return Result.success(num);
    }

    @GetMapping("getCommentNumToMe")
    public Result getCommentNumToMe(@RequestHeader("token") String jwt)
    {
        Map<String,Object> chaims= JwtUtils.parseJWT(jwt);
        Integer UserId=(Integer) chaims.get("userid");
        int num=commentMapper.getCommentNumToMe(UserId);
        return Result.success(num);
    }









}
