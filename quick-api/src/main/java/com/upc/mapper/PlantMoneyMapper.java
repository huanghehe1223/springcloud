package com.upc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upc.entity.PlantMoney;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PlantMoneyMapper extends BaseMapper<PlantMoney> {

    // 平台金额的变化
    @Update("update plantmoney set money =money+#{money}")
    int updatePlantMoney(Double money);

    @Select("select money from plantmoney where id = 1")
    Double selectPlantMoney();

    @Select("SELECT COUNT(*) FROM record WHERE toUserId != 0")
    int selectProfit();

    @Select("SELECT SUM(money) FROM record WHERE toUserId = 0")
    Double getRunning();

    @Select("SELECT SUM(money) FROM withdraw WHERE state = 1")
    Double getOut();
}
