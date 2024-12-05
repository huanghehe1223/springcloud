package com.upc.controller.admin;


import com.upc.common.Result;
//import com.upc.config.JsonRedisTemplate;
import com.upc.service.IPlantMoneyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.geom.QuadCurve2D;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/plantMoney")
@Tag(name = "plantMoney管理")
public class PlantMoneyController {
    @Autowired
    private IPlantMoneyService plantMoneyService;
//    @Autowired
//    JsonRedisTemplate jsonRedisTemplate;

    @GetMapping()
    public Result getPlantMoney(){
        Double money= plantMoneyService.getPlantMoney();
//        JsonRedisTemplate.opsForValue().set("plantMoney",money);
        // Map
//        Map<String, Object> map = new HashMap<>();
//        map.put("title", "spring 中文网");
//        map.put("url", "https://springdoc.cn");
//        map.put("createAt", LocalDateTime.now());
//        // 设置 key/value
//        this.jsonRedisTemplate.opsForValue().set("planMoney", money);
//        // 读取 key/value
//        Double plantmoney =(Double)this.jsonRedisTemplate.opsForValue().get("planMoney");
        return Result.success(money);
    }
    @GetMapping("/update/{plantMoney}")
    public Result updatePlantMoney(@PathVariable Double plantMoney) {
        boolean success = plantMoneyService.updatePlantMoney(plantMoney);
        if (success) {
            return Result.success();
        } else {
            return Result.error("更新失败");
        }
    }

    @GetMapping("/getProfit")
    public Result getProfit() {
        Double profit = plantMoneyService.getProfit();
        return Result.success(profit);
    }

    @GetMapping("/getRunning")
    public Result getRunning() {
        Double running = plantMoneyService.getRunning();
        return Result.success(running);
    }
    @GetMapping("/getOut")
    public Result getOut() {
        Double out = plantMoneyService.getOut();
        return Result.success(out);
    }

}
    // 查询所有}
