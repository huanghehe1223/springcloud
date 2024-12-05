package com.upc.service;

import com.upc.entity.Withdraw;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lww
 * @since 2024-07-20
 */
public interface IWithdrawService  {
    List<Withdraw> getAllWithdrawals(Integer pageNum, Integer pageSize);
    Withdraw getWithdrawById(Integer id);
    boolean createWithdraw(Withdraw withdraw);
    boolean updateWithdraw(Withdraw withdraw);
    boolean deleteWithdraw(Integer id);

}
