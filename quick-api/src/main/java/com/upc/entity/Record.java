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
@AllArgsConstructor
@NoArgsConstructor
@TableName("record")
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer toUserId;

    private Integer fromUserId;
    private LocalDateTime endTime;

    private Integer taskId;

    private Double money;
    private  String alipayId;
    Integer state;

    @TableField(exist = false)
    private String toUsername;
    @TableField(exist = false)
    private String fromUsername;
    @TableField(exist = false)
    private String toUserImage;
    @TableField(exist = false)
    private String fromUserImage;


}
