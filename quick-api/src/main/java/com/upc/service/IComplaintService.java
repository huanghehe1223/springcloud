package com.upc.service;

import com.upc.entity.Complaint;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lww
 * @since 2024-07-20
 */
public interface IComplaintService {


    boolean addComplaint(Complaint complaint);
    boolean deleteComplaint(Integer id);
    boolean updateComplaint(Complaint complaint);
    Complaint getComplaintById(Integer id);
    List<Complaint> getAllComplaints(Integer pageNum,Integer pageSize);


    List<Complaint> getMyComplaint(Integer pageNum, Integer pageSize,Integer userId);
}
