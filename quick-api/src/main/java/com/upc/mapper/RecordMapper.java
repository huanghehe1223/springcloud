package com.upc.mapper;

import com.upc.entity.Record;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upc.entity.RecordDTO;
import com.upc.entity.UserRecordCountDTO;
import com.upc.entity.UserTransactionDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lww
 * @since 2024-07-20
 */
public interface RecordMapper extends BaseMapper<Record> {

    @Select("SELECT r.toUserId, COUNT(*) as count, SUM(r.money) as money, u.username as toUsername " +
            "FROM record r " +
            "LEFT JOIN user u ON r.toUserId = u.userid " +
            "WHERE toUserId != 0 " +
            "GROUP BY r.toUserId " +
            "ORDER BY count DESC,money DESC")
    List<UserRecordCountDTO> getUserRecordCount();


    @Select("SELECT endTime , money FROM record WHERE toUserId = #{userId}")
    List<Record> getIncomeRecords(@Param("userId") int userId);

    @Select("SELECT endTime ,money FROM record WHERE fromUserId = #{userId} and toUserId != 0")
    List<Record> getExpenseRecords(@Param("userId") int userId);


    @Select("SELECT endTime ,money FROM record WHERE fromUserId = #{userId} and toUserId = 0")
    List<Record> getExpenseRecords2(@Param("userId") int userId);

    @Select("SELECT DATE(endTime) AS time, COALESCE(SUM(money), 0) AS totalMoney " +
            "FROM record " +
            "WHERE endTime BETWEEN #{startTime} AND #{endTime} " +
            "AND toUserId = 0 " +
            "GROUP BY DATE(endTime)")
    List<RecordDTO> getRecordMoneySum(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);



}
