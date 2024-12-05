package com.upc.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeVO {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
