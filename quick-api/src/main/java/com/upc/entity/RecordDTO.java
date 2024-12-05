package com.upc.entity;



import lombok.Data;

import java.time.LocalDate;

@Data
public class RecordDTO {
    private LocalDate time; // 某一天
    private Double totalMoney; // 这一天的 money 总和
}