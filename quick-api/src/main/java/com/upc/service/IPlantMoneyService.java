package com.upc.service;

import com.upc.entity.Complaint;

public interface IPlantMoneyService {
    boolean updatePlantMoney(Double plantMoney);
    Double getPlantMoney();
    Double getProfit();

    Double getRunning();

    Double getOut();
}
