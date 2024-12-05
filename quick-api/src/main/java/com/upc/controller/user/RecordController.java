package com.upc.controller.user;


import com.upc.common.Result;
import com.upc.entity.*;
import com.upc.service.IRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.upc.common.Result;
import com.upc.service.IRecordService;
import com.upc.service.impl.RecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.upc.entity.Record;

import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lww
 * @since 2024-07-20
 */
@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    IRecordService recordService;


    @PostMapping("/addRecord")
    public Result addRecord(Record record)//订单完成增加一个record，fromid和toid就是publishid和acceptid
    {
        recordService.addRecord(record);
        return Result.success();

    }
    @GetMapping("/deleteRecord")
    public Result deleteRecord(Integer id)
    {
        int ok=recordService.deleteRecord(id);
        if (ok==0)
        {
            return Result.error("删除失败");
        }
        return Result.success();

    }

    @GetMapping("getAllRecharge")
    public Result getALLRecharge()
    {
       List<Record> records= recordService.getAllRecharge();
        return Result.success(records);
    }

    @GetMapping("getAllNonRecharge")
    public Result getAllNonRecharge()
    {
        List<Record> records=recordService.getAllNonRecharge();
        return Result.success(records);
    }


    //lww
    @GetMapping("/get/{id}")
    public Result getRecordById(@PathVariable int id) {
        Record record = recordService.getRecordById(id);
        if (record != null) {
            return Result.success(record);
        } else {
            return Result.error("Failed to find the record");
        }
    }

    @GetMapping("/list")
    public Result getAllRecords(Integer pageNum, Integer pageSize) {
        List<Record> records = recordService.getAllRecords(pageNum,pageSize);
        if (records != null) {
            return Result.success(records);
        } else {
            return Result.error("Failed to load records");
        }
    }

    @PostMapping("/recordMoneySum")
    @Operation(summary = "收入折线图")
    public Result getRecordMoneySum(@RequestBody TimeVO timeVO) {
        List<RecordDTO> recordMoneySums = recordService.getRecordMoneySum(timeVO);
        if (recordMoneySums != null) {
            return Result.success(recordMoneySums);
        } else {
            return Result.error("Failed to load record money sums");
        }
    }

    @GetMapping("getAllRechargeAndWithdraw")
   private Result getAllRechargeAndWithdraw()
   {
       List<RechargeWithdrawDTO> rechargeWithdrawDTOS=recordService.getAllRechargeAndWithdraw();

       return Result.success(rechargeWithdrawDTOS);
   }




}
