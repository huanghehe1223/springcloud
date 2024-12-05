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
@TableName("comment")
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer star;
    private String context;
    private LocalDateTime createTime;
    private Integer fromUserId;
    private Integer toUserId;
    private Integer anony;
    @TableField(exist = false)
    private String fromUsername;
    @TableField(exist = false)
    private String toUsername;
    @TableField(exist = false)
    private String fromUserImage;
    @TableField(exist = false)
    private String toUserImage;

    @TableField(exist = false)
    private String fromUserPhone;
    @TableField(exist = false)
    private String toUserPhone;


}