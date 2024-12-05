package com.upc.mapper;

import com.upc.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lww
 * @since 2024-07-20
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Update("update user set balance = balance - #{money} where userid = #{id}")
    int updateUserBalance(Double money,Integer id);
}
