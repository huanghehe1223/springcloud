package com.upc.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserTransactionDTO {
    private String inOut;
    private LocalDateTime time;
    private String money;
}