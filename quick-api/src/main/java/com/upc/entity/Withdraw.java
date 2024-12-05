package com.upc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author lww
 * @since 2024-07-20
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("withdraw")
public class Withdraw implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Double money;

    private String code;

    private Integer userId;
    private LocalDateTime createTime;
    private Integer state;
    private LocalDateTime endTime;
    @TableField(exist = false)
    private String username;
    @TableField(exist = false)
    private String userImage;


}
