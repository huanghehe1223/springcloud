package com.upc.mapper;

import com.upc.entity.UserTransactionDTO;
import com.upc.entity.Withdraw;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lww
 * @since 2024-07-20
 */
public interface WithdrawMapper extends BaseMapper<Withdraw> {
//    @Select("SELECT '支出' AS inOut, createTime AS time, money " +
//            "FROM withdraw " +
//            "WHERE userId = #{userId} AND state = 1")
//    List<UserTransactionDTO> getUserWithdrawals(@Param("userId") int userId);


    @Select("SELECT createTime , money FROM withdraw WHERE userId = #{userId} AND state = 1")
    List<Withdraw> getUserWithdrawals(@Param("userId") int userId);

    @Update("UPDATE user SET balance = balance- #{money} where userid = #{userId}")
    int  updateUserBalance(Integer userId,Double  money);

}
