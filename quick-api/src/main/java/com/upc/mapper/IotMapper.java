package com.upc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upc.entity.QueryRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IotMapper extends BaseMapper<QueryRecord> {
}
