package com.upc.controller.user;


import com.upc.common.Result;
import com.upc.entity.Withdraw;
import com.upc.service.IWithdrawService;
import com.upc.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lww
 * @since 2024-07-20
 */
@RestController
@RequestMapping("/withdraw")
@Tag(name = "提现余额相关")
public class WithdrawController {
    @Autowired
    private IWithdrawService withdrawService;

    @GetMapping
    @Operation(summary = "获取所有提现记录")
    public Result getAllWithdrawals(Integer pageNum, Integer pageSize) {
        List<Withdraw> lists = withdrawService.getAllWithdrawals(pageNum,pageSize);
        return  Result.success(lists);
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据id获取提现记录")
    public Result getWithdrawById(@PathVariable Integer id) {
        Withdraw one= withdrawService.getWithdrawById(id);
        return Result.success(one);
    }

    @PostMapping("/addwithdraw")
    @Operation(summary = "提现申请发起")
    public Result createWithdraw(@RequestBody Withdraw withdraw,@RequestHeader("token") String jwt) {
        Map<String,Object> chaims= JwtUtils.parseJWT(jwt);
         Integer userid=(Integer) chaims.get("userid");
        withdraw.setUserId(userid);
        withdraw.setCreateTime(LocalDateTime.now());
        boolean success=withdrawService.createWithdraw(withdraw);
        if(success){
            return Result.success();
        }else {
            return Result.error("提现申请失败");
        }

    }

    @PutMapping("/updatewithdraw")
    @Operation(summary = "提现申请修改")
    public Result updateWithdraw(@RequestBody Withdraw withdraw) {
        boolean success=withdrawService.updateWithdraw(withdraw);
        if(success){
            return Result.success(withdraw);
        }else {
            return Result.error("您的余额不足，提现申请修改失败");
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除提现申请")
    public Result deleteWithdraw(@PathVariable Integer id) {

        boolean success = withdrawService.deleteWithdraw(id);
        if (success) {
            return Result.success();
        } else {
            return Result.error( "Failed to delete task");
        }
    }

}
