package com.upc.controller.user;


import com.upc.common.Result;
import com.upc.entity.Task;
import com.upc.entity.TaskDTO;
import com.upc.entity.TimeVO;
import com.upc.mapper.TaskMapper;
import com.upc.service.ITaskService;
import com.upc.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
@RequestMapping("/task")
@Tag(name = "发布任务管理")
public class TaskController {
    @Autowired
    private ITaskService taskService;
    @Autowired
    private TaskMapper taskMapper;

    @PostMapping("/add")
    @Operation(summary = "发布任务")
    public Result addTask(@RequestBody Task task,@RequestHeader("token") String jwt) {
        Map<String,Object> chaims= JwtUtils.parseJWT(jwt);
        Integer userid=(Integer) chaims.get("userid");
        task.setPublishUserId(userid);
        System.out.println(task);
        if (task.getImageUrl()==null||"".equals(task.getImageUrl()))
        {
            String before= "https://ui-avatars.com/api/?name=";
            String after="&background=455a64&color=ffffff";
            if (task.getTitle()!=null&&!task.getTitle().equals(""))
             task.setImageUrl(before+task.getTitle()+after);
            else task.setImageUrl(before+"订单"+after);
        }
        boolean success = taskService.addTask(task);

        if (success) {
            return Result.success();
        } else {
            return Result.error("Failed to add task，您的余额不足");
        }
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除任务")
    public Result deleteTask(Integer id) {
        boolean success = taskService.deleteTaskById(id);
        if (success) {
            return Result.success();
        } else {
            return Result.error( "Failed to delete task");
        }
    }

    @PutMapping("/update")
    @Operation(summary = "修改任务")
    public Result updateTask(@RequestBody Task task) {
        boolean success = taskService.updateTask(task);
        if (success) {
            return Result.success(task);
        } else {
            return Result.error("Failed to update task");
        }
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "根据id查询任务")
    public Result getTaskById(@PathVariable   int id) {
        Task task= taskService.getTaskById(id);
        if (task!=null) {
            return Result.success(task);
        } else {
            return Result.error("Failed to find a task");
        }
    }

    @GetMapping("/list")
    @Operation(summary = "查询所有任务")
    public Result  getAllTask(Integer pageNum, Integer pageSize) {
        List<Task> tasks= taskService.getAllTask(pageNum,pageSize);
         return Result.success(tasks);
    }
    @GetMapping("/listUndo")
    @Operation(summary = "查询所有未结单任务")
    public Result  getAllTaskUndo(Integer pageNum, Integer pageSize) {
        List<Task> tasks= taskService.getAllTaskUndo(pageNum,pageSize);
        return Result.success(tasks);
    }

    @GetMapping("/listUndoTotal")
    @Operation(summary = "查询所有未结单任务的数量")
    public Result  getAllTaskUndoTotal() {
        Integer tasks= taskService.getAllTaskUndoTotal();
        return Result.success(tasks);
    }

    @GetMapping("/getAllTask1")
    @Operation(summary = "查询所有未结单任务的数量")
    public Result  getAllTask1() {
        Integer tasks= taskMapper.getAllTask1();
        return Result.success(tasks);
    }
    @GetMapping("/getAllTask2")
    @Operation(summary = "查询所有未结单任务的数量")
    public Result  getAllTask2() {
        Integer tasks= taskMapper.getAllTask2();
        return Result.success(tasks);
    }
    @GetMapping("/getAllTask3")
    @Operation(summary = "查询所有未结单任务的数量")
    public Result  getAllTask3() {
        Integer tasks= taskMapper.getAllTask3();
        return Result.success(tasks);
    }


    //我发布的任务

    @GetMapping("/getPublishTask")
    public Result getPublishTask(Integer pageNum, Integer pageSize,Integer publishUserId,@RequestHeader("token") String jwt)
    {
        Map<String,Object> chaims= JwtUtils.parseJWT(jwt);
        Integer userid=(Integer) chaims.get("userid");
        publishUserId=userid;
        List<Task> taskList=taskService.getPublishTask(pageNum,pageSize,publishUserId);


        return Result.success(taskList);
    }
    @GetMapping("/getPublishTaskTotal")
    public Result getPublishTaskTotal(Integer publishUserId,@RequestHeader("token") String jwt)
    {
        Map<String,Object> chaims= JwtUtils.parseJWT(jwt);
        Integer userid=(Integer) chaims.get("userid");
        publishUserId=userid;
        Integer sum=taskService.getPublishTaskTotal(publishUserId);


        return Result.success(sum);
    }
    @GetMapping("/getPublishTaskDiverse")
    public Result getPublishTaskDiverse(Integer pageNum, Integer pageSize,Integer publishUserId,Integer state,@RequestHeader("token") String jwt)
    {
        Map<String,Object> chaims= JwtUtils.parseJWT(jwt);
        Integer userid=(Integer) chaims.get("userid");
        publishUserId=userid;
        List<Task> taskList=taskService.getPublishTaskDiverse(pageNum,pageSize,publishUserId,state);
        return Result.success(taskList);
    }

    //我接受的任务
    @GetMapping("/getAcceptTask")
    public Result getAcceptTask(Integer pageNum, Integer pageSize,Integer acceptUserId,@RequestHeader("token") String jwt)
    {
        Map<String,Object> chaims= JwtUtils.parseJWT(jwt);
        Integer userid=(Integer) chaims.get("userid");
        acceptUserId=userid;
        List<Task> taskList=taskService.getAcceptTask(pageNum,pageSize,acceptUserId);
        return Result.success(taskList);
    }

    @GetMapping("/getAcceptTaskTotal")
    public Result getAcceptTaskTotal(Integer acceptUserId,@RequestHeader("token") String jwt)
    {
        Integer sum=taskService.getAcceptTaskTotal(acceptUserId);
        return Result.success(sum);
    }

    @GetMapping("/getAcceptTaskDiverse")
    public Result getAcceptTaskDiverse(Integer pageNum, Integer pageSize,Integer acceptUserId,Integer state,@RequestHeader("token") String jwt)
    {
        Map<String,Object> chaims= JwtUtils.parseJWT(jwt);
        Integer userid=(Integer) chaims.get("userid");
        acceptUserId=userid;
        List<Task> taskList=taskService.getAcceptTaskDiverse(pageNum,pageSize,acceptUserId,state);
        return Result.success(taskList);
    }


    @PostMapping("/taskCount")
    @Operation(summary = "任务数量折线图")
    public Result getTaskCount(@RequestBody TimeVO timeVO){
        List<TaskDTO> taskCounts = taskService.getTaskCount(timeVO);
        if (taskCounts != null) {
            return Result.success(taskCounts);
        } else {
            return Result.error("Failed to load task counts");
        }
    }



    @PutMapping("/updateTaskState")
    @Operation(summary = "更新task状态")
    public Result updateTaskState(@RequestBody Task task,@RequestHeader("token") String jwt){
        int state=task.getState();
        int taskId=task.getId();
        boolean success=false;

        Map<String,Object> chaims= JwtUtils.parseJWT(jwt);
        Integer userid=(Integer) chaims.get("userid");
        Integer acceptUserId=userid;
        if(state==1) {

            success = taskService.updateTaskState1(acceptUserId,taskId);
        }
        else if(state==2) {
            success = taskService.updateTaskState2(taskId);
        }
        else if(state==3) {
            success = taskService.updateTaskState3(taskId);
        }
        if(success){
            return Result.success(null);
        }else{
            return Result.error("Failed to update task state");
        }
    }





}
