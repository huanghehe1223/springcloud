package com.upc.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upc.entity.Comment;
import com.upc.entity.User;
import com.upc.entity.Withdraw;
import com.upc.mapper.UserMapper;
import com.upc.mapper.WithdrawMapper;
import com.upc.service.IWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class WithdrawServiceImpl  implements IWithdrawService {
    @Autowired
    private WithdrawMapper withdrawMapper;
    @Autowired
    private UserMapper userMapper;


    @Override
    public List<Withdraw> getAllWithdrawals(Integer pageNum, Integer pageSize) {
        Page<Withdraw> page = new Page<>(pageNum, pageSize);
        Page<Withdraw> commentPage= withdrawMapper.selectPage(page, null);
        List<Withdraw> withdrawals = commentPage.getRecords();

//        List<Withdraw> withdrawals = withdrawMapper.selectList(null);
        for (Withdraw withdrawal : withdrawals) {
            if(withdrawal.getUserId()!=null)
            {
                User user= userMapper.selectById(withdrawal.getUserId());
                withdrawal.setUsername(user.getUsername());
                withdrawal.setUserImage(user.getImageUrl());
            }
            else
            {
                withdrawal.setUsername(" ");
                withdrawal.setUserImage(" ");
            }

        }
        return withdrawals;
    }

    @Override
    public Withdraw getWithdrawById(Integer id) {
        Withdraw withdrawal=withdrawMapper.selectById(id);
        if(withdrawal.getUserId()!=null)
        {
            User user= userMapper.selectById(withdrawal.getUserId());
            withdrawal.setUsername(user.getUsername());
            withdrawal.setUserImage(user.getImageUrl());
        }
        else
        {
            withdrawal.setUsername(" ");
            withdrawal.setUserImage(" ");
        }

        return withdrawal;
    }

    @Override
    public boolean createWithdraw(Withdraw withdraw) {
        int success= withdrawMapper.insert(withdraw);
        if(success>0){
            withdrawMapper.updateUserBalance(withdraw.getUserId(),withdraw.getMoney());
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean updateWithdraw(Withdraw withdraw) {
        return withdrawMapper.updateById(withdraw)>0;
    }

    @Override
    public boolean deleteWithdraw(Integer id) {
        return withdrawMapper.deleteById(id)>0;
    }

}
