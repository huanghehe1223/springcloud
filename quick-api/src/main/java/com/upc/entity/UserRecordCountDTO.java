package com.upc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class UserRecordCountDTO {
    private static final long serialVersionUID = 1L;
    private Integer toUserId;
    private Long count;

    private Double money;
    @TableField(exist = false)
    private String toUsername;

}
