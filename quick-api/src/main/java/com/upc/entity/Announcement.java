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
@TableName("announcement")
public class Announcement implements Serializable
{
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String title;
    private String context;
    private LocalDateTime createTime;
    private Integer createId;
    @TableField(exist = false)
    private String adminname;
    private String imageUrl;
    private String tags;
    private String category;
    private String adminImageUrl;
    private Integer visible;
}
