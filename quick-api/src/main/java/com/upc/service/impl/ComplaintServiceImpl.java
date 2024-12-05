package com.upc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upc.entity.Comment;
import com.upc.entity.Complaint;
import com.upc.entity.Task;
import com.upc.entity.User;
import com.upc.mapper.ComplaintMapper;
import com.upc.mapper.TaskMapper;
import com.upc.mapper.UserMapper;
import com.upc.service.IComplaintService;
import com.upc.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lww
 * @since 2024-07-20
 */
@Service
public class ComplaintServiceImpl  implements IComplaintService {

    @Autowired
    private ComplaintMapper complaintMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ITaskService taskService;
    @Autowired
    TaskMapper taskMapper;

    @Override
    public boolean addComplaint(Complaint complaint) {
        complaint.setCreateTime(LocalDateTime.now());
        taskService.updateTaskState3(complaint.getTaskId());
        return complaintMapper.insert(complaint) > 0;
    }

    @Override
    public boolean deleteComplaint(Integer id) {
        return complaintMapper.deleteById(id) > 0;
    }

    @Override
    public boolean updateComplaint(Complaint complaint) {
        return complaintMapper.updateById(complaint) > 0;
    }

    @Override
    public Complaint  getComplaintById(Integer id) {
        Complaint complaint = complaintMapper.selectById(id);
        if (complaint == null) {
            return null;
        }
        if(complaint.getUserId()!=null){
            User user = userMapper.selectById(complaint.getUserId());
            complaint.setUsername(user.getUsername());
            complaint.setUserImage(user.getImageUrl());

        }
        else
        {
            complaint.setUsername(" ");
            complaint.setUserImage(" ");
        }

        return complaint;
    }

    @Override
    public List<Complaint> getAllComplaints(Integer pageNum, Integer pageSize) {
        Page<Complaint> page = new Page<>(pageNum, pageSize);
        Page<Complaint> commentPage= complaintMapper.selectPage(page, null);
        List<Complaint> complaints = commentPage.getRecords();

//        List<Complaint>  = complaintMapper.selectList(null);
        for (Complaint complaint : complaints) {
            if(complaint.getUserId()!=null){
                User user = userMapper.selectById(complaint.getUserId());
                complaint.setUsername(user.getUsername());
                complaint.setUserImage(user.getImageUrl());
              complaint.setFromUserPhone(user.getPhone());
                LambdaQueryWrapper<Task> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(Task::getId, complaint.getTaskId());
                Task task = taskMapper.selectOne(queryWrapper);
                if(task!=null){
                    int acceptUserId = task.getAcceptUserId();
                    User acceptUser = userMapper.selectById(acceptUserId);
                    String phone = acceptUser.getPhone();
                    complaint.setToUserPhone(phone);
                }else{
                    complaint.setToUserPhone(" ");
                }
            }
            else
            {
                complaint.setUsername(" ");
                complaint.setUserImage(" ");
            }
        }
        return complaints;
    }

    @Override
    public List<Complaint> getMyComplaint(Integer pageNum, Integer pageSize,Integer userId) {

        Page<Complaint> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Complaint> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Complaint::getUserId, userId);
        Page<Complaint> commentPage= complaintMapper.selectPage(page, queryWrapper);
        List<Complaint> complaints = commentPage.getRecords();


//        List<Complaint> complaints = complaintMapper.selectList(queryWrapper);
        for (Complaint complaint : complaints) {
            if(complaint.getUserId()!=null){
                User user = userMapper.selectById(complaint.getUserId());
                complaint.setUsername(user.getUsername());
                complaint.setUserImage(user.getImageUrl());

            }
            else
            {
                complaint.setUsername(" ");
                complaint.setUserImage(" ");
            }

        }
        return complaints;
    }
}
