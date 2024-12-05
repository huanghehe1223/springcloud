package com.upc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upc.entity.*;
import com.upc.entity.Record;
import com.upc.mapper.RecordMapper;
import com.upc.mapper.UserMapper;
import com.upc.mapper.WithdrawMapper;
import com.upc.service.IPlantMoneyService;
import com.upc.service.IRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lww
 * @since 2024-07-20
 */
@Service
public class RecordServiceImpl implements IRecordService {
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IPlantMoneyService plantMoneyService;
    @Autowired
    WithdrawMapper withdrawMapper;

    @Override
    public void addRecord(Record record) {
        recordMapper.insert(record);

    }

    @Override
    public int deleteRecord(Integer id) {
        int ok=recordMapper.deleteById(id);
        return ok;
    }

    @Override
    public List<Record> getAllRecharge() {
        LambdaQueryWrapper<Record> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Record::getToUserId,0);
        queryWrapper.eq(Record::getState,1);
       List<Record> records= recordMapper.selectList(queryWrapper);
        for (Record record : records) {
            if(record.getFromUserId()!=null)
            {
                User user = userMapper.selectById(record.getFromUserId());
                record.setFromUsername(user.getUsername());
                record.setFromUserImage(user.getImageUrl());
            }
            else
            {
                record.setFromUsername(" ");
                record.setFromUserImage(" ");
            }
            record.setToUsername("快客平台");
            record.setToUserImage(" ");
        }
        return records;
    }

    @Override
    public List<Record> getAllNonRecharge() {
        LambdaQueryWrapper<Record> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(Record::getToUserId,0);
        List<Record> records= recordMapper.selectList(queryWrapper);
        for (Record record : records)
        {
            if(record.getFromUserId()!=null)
            {
                User user=null;
                user = userMapper.selectById(record.getFromUserId());
                if(user==null)
                {
                    record.setFromUsername(" ");
                    record.setFromUserImage(" ");
                }else {
                    record.setFromUsername(user.getUsername());
                    record.setFromUserImage(user.getImageUrl());
                }
            }
            else
            {
                record.setFromUsername(" ");
                record.setFromUserImage(" ");
            }
            if(record.getToUserId()!=null)
            {
                User user=null;
                user = userMapper.selectById(record.getToUserId());
                if(user==null){
                    record.setToUsername(" ");
                    record.setToUserImage(" ");
                }else {
                    record.setToUsername(user.getUsername());
                    record.setToUserImage(user.getImageUrl());
                }
            }
            else
            {
                record.setToUsername(" ");
                record.setToUserImage(" ");
            }

        }
        return records;
    }




    //lww
    @Override
    public Record getRecordById(int id) {
        return recordMapper.selectById(id);
    }

    @Override
    public List<Record> getAllRecords(Integer pageNum, Integer pageSize) {
        Page<Record> page=new Page<>(pageNum, pageSize);
        Page<Record> recordPage=recordMapper.selectPage(page, null);
        List<Record> records = recordPage.getRecords();
        return  records;
//        return recordMapper.selectList(null);
    }

    @Override
    public List<UserRecordCountDTO> getUserRecordCount() {
        return recordMapper.getUserRecordCount();
    }

    @Override
    public List<RecordDTO> getRecordMoneySum(TimeVO timeVO) {
        List<RecordDTO> recordMoneySums = recordMapper.getRecordMoneySum(timeVO.getStartTime(), timeVO.getEndTime());

        // 将结果转换为 Map 以便于快速查找
        Map<LocalDate, Double> recordMoneySumMap = recordMoneySums.stream()
                .collect(Collectors.toMap(RecordDTO::getTime, RecordDTO::getTotalMoney));

        // 初始化结果列表
        List<RecordDTO> result = new ArrayList<>();

        // 遍历时间区间，确保每一天都有记录
        LocalDate startDate = timeVO.getStartTime().toLocalDate();
        LocalDate endDate = timeVO.getEndTime().toLocalDate();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            RecordDTO dto = new RecordDTO();
            dto.setTime(date);
            dto.setTotalMoney(recordMoneySumMap.getOrDefault(date, 0.0));
            result.add(dto);
        }

        return result;
    }

    @Override
    public List<RechargeWithdrawDTO> getAllRechargeAndWithdraw() {

        List<Record> records = getAllRecharge();
        List<RechargeWithdrawDTO> result = new ArrayList<>();
        for (Record record : records) {
            RechargeWithdrawDTO dto = new RechargeWithdrawDTO();
            dto.setMoney(record.getMoney());
            dto.setUsername(record.getFromUsername());
            dto.setEssence(0);
            dto.setUserImage(record.getFromUserImage());
            dto.setEndTime(record.getEndTime());
            result.add(dto);
        }
        LambdaQueryWrapper<Withdraw> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Withdraw::getState,1);
        List<Withdraw> withdraws = withdrawMapper.selectList(queryWrapper);
        for (Withdraw withdraw : withdraws) {
            RechargeWithdrawDTO dto = new RechargeWithdrawDTO();
            dto.setMoney(withdraw.getMoney());
            dto.setEndTime(withdraw.getEndTime());
            dto.setEssence(1);
            if (withdraw.getUserId()!=null)
            {
                User user = userMapper.selectById(withdraw.getUserId());
                dto.setUsername(user.getUsername());
                dto.setUserImage(user.getImageUrl());
            }
            else
            {
                dto.setUsername(" ");
                dto.setUserImage(" ");
            }
            result.add(dto);

        }
        System.out.println(result);
        result.sort(Comparator.comparing(RechargeWithdrawDTO::getEndTime,Comparator.nullsLast(Comparator.reverseOrder())));

        return result;
    }
}
