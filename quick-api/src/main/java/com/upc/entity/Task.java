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
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("task")
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)

    private Integer id;
    private Integer publishUserId;
    private Integer acceptUserId;
    private Double reward;
    private LocalDateTime createTime;
    private LocalDateTime takeTime;
    private  LocalDateTime appointTime;
    private LocalDateTime pickTime;
    private LocalDateTime receiveTime;

    //截止时间
    private LocalDateTime endTime;
    private String title;
    private String context;
    private Integer state;//    状态 0:未接单 1:已取件 2:已完成
    private String fromPlace;
    private String toPlace;
    private String categories;
    private String attachments;
    String imageUrl;
    @TableField(exist = false)
    private String publishUsername;
    @TableField(exist = false)
    private String acceptUsername;
    @TableField(exist = false)
    private String acceptUserPhone;

}