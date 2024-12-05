package com.upc.controller.user;


import com.upc.common.Result;
import com.upc.entity.Complaint;
import com.upc.service.IComplaintService;
import com.upc.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/complaint")
@Tag(name="投诉相关")
public class ComplaintController {
    @Autowired
    private IComplaintService complaintService;

    @PostMapping("/add")
    @Operation(summary = "发布申诉")
    public Result addComplaint(@RequestBody Complaint complaint) {
        boolean success = complaintService.addComplaint(complaint);

        if (success) {
            return Result.success();
        } else {
            return Result.error("Failed to add complaint");
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除申诉")
    public Result deleteComplaint(@PathVariable Integer id) {
        boolean success = complaintService.deleteComplaint(id);
        if (success) {
            return Result.success();
        } else {
            return Result.error( "Failed to delete complaint");
        }
    }

    @PutMapping("/update")
    @Operation(summary = "修改申诉")
    public Result updateComplaint(@RequestBody Complaint complaint) {
        boolean success = complaintService.updateComplaint(complaint);
        if (success) {
            return Result.success(complaint);
        } else {
            return Result.error("Failed to update complaint");
        }
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "根据id查询申诉")
    public Result getComplaintById(@PathVariable   Integer id) {
        Complaint complaint= complaintService.getComplaintById(id);
        if (complaint!=null) {
            return Result.success(complaint);
        } else {
            return Result.error("Failed to find a complaint");
        }
    }

    @GetMapping("/list")
    @Operation(summary = "查询所有申诉")
    public Result  getAllComplaint(Integer pageNum,Integer pageSize) {
        List<Complaint> complaints= complaintService.getAllComplaints(pageNum,pageSize);
        if (complaints!=null) {
            return Result.success(complaints);
        } else {
            return Result.error("Failed to load complaints");
        }
    }

    @GetMapping("/getMyComplaint")
    public Result getMyComplaint(Integer pageNum, Integer pageSize,Integer userId,@RequestHeader("token") String jwt)
    {
        Map<String,Object> chaims= JwtUtils.parseJWT(jwt);
        userId=(Integer) chaims.get("userid");
        List<Complaint> complaintList=complaintService.getMyComplaint(pageNum,pageSize,userId);
        return Result.success(complaintList);
    }

}
