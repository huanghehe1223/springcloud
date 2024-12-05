package com.upc.service;

import com.upc.entity.*;

import java.util.List;

import com.upc.entity.Record;
import com.upc.entity.RecordDTO;
import com.upc.entity.TimeVO;
import com.upc.entity.UserRecordCountDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lww
 * @since 2024-07-20
 */
public interface IRecordService{

    //hh
    void addRecord(Record record);

    int deleteRecord(Integer id);

    List<Record> getAllRecharge();

    List<Record> getAllNonRecharge();
    //lww
    Record getRecordById(int id);
    List<Record> getAllRecords(Integer pageNum, Integer pageSize);
    List<UserRecordCountDTO> getUserRecordCount();
    List<RecordDTO> getRecordMoneySum(TimeVO timeVO);

    List<RechargeWithdrawDTO> getAllRechargeAndWithdraw();
}
