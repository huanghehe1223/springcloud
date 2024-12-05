package com.upc.service.impl;

import com.upc.mapper.PlantMoneyMapper;
import com.upc.service.IPlantMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlantMoneyServiceImpl  implements IPlantMoneyService {

    @Autowired
    private PlantMoneyMapper plantMoneyMapper;

    // 这里应该有数据库的调用
    @Override
    public boolean updatePlantMoney(Double plantMoney) {
        return plantMoneyMapper.updatePlantMoney(plantMoney)>0;
    }

    @Override
    public Double getPlantMoney() {
        return plantMoneyMapper.selectPlantMoney();
    }

    @Override
    public Double getProfit() {
        return  plantMoneyMapper.selectProfit()*0.5;
    }

    @Override
    public Double getRunning() {
        return plantMoneyMapper.getRunning();
    }

    @Override
    public Double getOut() {
        return plantMoneyMapper.getOut();
    }
}
