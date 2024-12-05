package com.upc.entity;

import lombok.Data;

@Data
public class WalletDTO {
    private Double income;
    private Double outcome;
    private int ingTaskNum;
    private int edTaskNum;
    private int abTaskNum;
    private int unTaskNum;
    private int totalTaskNum;
    private Double totalChongzhi;
    private Double totalWithdraw;

}
