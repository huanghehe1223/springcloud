package com.upc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@NoArgsConstructor
@AllArgsConstructor
//@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "userid", type = IdType.AUTO) // 指定主键字段和主键生成策略
    private Long userid;
    private String username;
    private String password;
    private String email;
    private String phone;
    private Integer schoolId;
    private Integer sex;
    private String nickName;
    private LocalDateTime createTime;
    private double balance;
    private int state;
    private String openid;
    private String ticket;
    private int score;
    private double deliverReward;
    private String imageUrl;
    private String sentence;

}