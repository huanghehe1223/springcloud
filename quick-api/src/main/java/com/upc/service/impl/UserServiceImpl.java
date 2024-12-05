package com.upc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upc.entity.*;
import com.upc.entity.Record;
import com.upc.mapper.RecordMapper;
import com.upc.mapper.UserMapper;
import com.upc.mapper.WithdrawMapper;
import com.upc.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.baomidou.mybatisplus.core.toolkit.Wrappers.lambdaQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
public class UserServiceImpl  implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private WithdrawMapper withdrawMapper;

    @Override
    public boolean deleteUserById(Integer userid) {
        return userMapper.deleteById(userid) > 0;
    }

    @Override
    public boolean updateUser(User user) {
        return userMapper.updateById(user) > 0;
    }

    @Override
    public User getUserById(Integer userid) {
        return userMapper.selectById(userid);
    }

    @Override
    public List<User> getAllUsers(Integer pageNum, Integer pageSize) {
        Page<User> page = new Page<>(pageNum, pageSize);
        Page<User> userPage= userMapper.selectPage(page, null);
        List<User> comments = userPage.getRecords();
        return comments;
    }

    @Override
    public List<User> getAllDeliveryUsers(Integer pageNum, Integer pageSize) {
        Page<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("score", 0); // 添加查询条件 score=0
        Page<User> userPage= userMapper.selectPage(page, queryWrapper);
        List<User> users = userPage.getRecords();
        return users;

//
//        return userMapper.selectList(queryWrapper);
    }



    @Override
    public List<UserTransactionDTO> getUserTransactions(Integer userId) {


        List<UserTransactionDTO> transactions = new ArrayList<>();

        List<Record> incomeRecords = recordMapper.getIncomeRecords(userId);
        List<Record> expenseRecords = recordMapper.getExpenseRecords(userId);
        List<Record> expenseRecords2 = recordMapper.getExpenseRecords2(userId);
//        System.out.println(incomeRecords);
//        System.out.println(expenseRecords);
        List<Withdraw> withdrawals = withdrawMapper.getUserWithdrawals(userId);

        for (Record record : incomeRecords) {
            if(record!=null){
                UserTransactionDTO dto = new UserTransactionDTO();
                dto.setInOut("收入");
                dto.setTime(record.getEndTime());
                Double money=record.getMoney();
                String strmoney="+"+money;
                dto.setMoney(strmoney);
                transactions.add(dto);
            }
        }

        for (Record record : expenseRecords) {
            if(record!=null){
                UserTransactionDTO dto = new UserTransactionDTO();
                dto.setInOut("支出");
                dto.setTime(record.getEndTime());
                Double money=record.getMoney();
                String strmoney="-"+money;
                dto.setMoney(strmoney);
                transactions.add(dto);
            }

        }
        for (Record record : expenseRecords2) {
            if(record!=null){
                UserTransactionDTO dto = new UserTransactionDTO();
                dto.setInOut("充值");
                dto.setTime(record.getEndTime());
                Double money=record.getMoney();
                String strmoney="+"+money;
                dto.setMoney(strmoney);
                transactions.add(dto);
            }

        }

        for (Withdraw withdraw : withdrawals) {
            if(withdraw!=null){
                UserTransactionDTO dto = new UserTransactionDTO();
                dto.setInOut("提现");
                dto.setTime(withdraw.getCreateTime());
                Double money=withdraw.getMoney();
                String strmoney="-"+money;
                dto.setMoney(strmoney);
                transactions.add(dto);
            }
        }

        // 想直接在原列表上进行排序，可以使用Collections.sort方法
        Collections.sort(transactions, Comparator.comparing(UserTransactionDTO::getTime).reversed());

        return transactions;
    }

    @Override
    public boolean updateUserBalance(Double money,Integer id) {
        boolean success=userMapper.updateUserBalance(money,id)>0;
        return success;
    }


    @Override
    public boolean register(User user) {

        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getEmail,user.getEmail());
        User user1=userMapper.selectOne(lambdaQueryWrapper);
        if (user1!=null)
        {
            return false;
        }
        else
        {
            userMapper.insert(user);
            return true;
        }
    }

    @Override
    public User login(User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getEmail,user.getEmail());
        lambdaQueryWrapper.eq(User::getPassword,user.getPassword());
        User user1=userMapper.selectOne(lambdaQueryWrapper);
        return user1;
    }




}
