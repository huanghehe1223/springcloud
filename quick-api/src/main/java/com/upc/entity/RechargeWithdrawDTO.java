package com.upc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RechargeWithdrawDTO {
    String username;
    String userImage;
    Double money;
    Integer essence;
    LocalDateTime endTime;

}
