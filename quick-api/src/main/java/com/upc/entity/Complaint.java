package com.upc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("complaint")
public class Complaint implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String context;
    private Integer taskId;
    private LocalDateTime createTime;
    private Integer state;
    private Integer userId;
    @TableField(exist = false)
    private  String username;
    private String title;
    @TableField(exist = false)
    private  String userImage;

    @TableField(exist = false)
    private  String fromUserPhone;
    @TableField(exist = false)
    private  String toUserPhone;


}