package com.upc.controller.admin;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.upc.common.Result;
import com.upc.entity.Announcement;
import com.upc.entity.Task;
import com.upc.service.IAnnouncementService;
import com.upc.utils.RedisUtils;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
@RequestMapping("/announcement")
@Tag(name = "公告管理")
public class AnnouncementController {
    @Autowired
    private IAnnouncementService announcementService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;




    @Autowired
    RedisUtils redisUtils;


    @PostMapping("/add")
    @Operation(summary = "添加公告")
    public Result addAnnouncement(@RequestBody Announcement announcement) {
        if (announcement.getImageUrl()==null||announcement.getImageUrl().equals(""))
        {
            if (announcement.getTitle()==null||announcement.getTitle().equals(""))
            {
                String before= "https://ui-avatars.com/api/?name=";
                String after="&background=455a64&color=ffffff";
                announcement.setImageUrl(before+"公告"+after);
            }
            else
            {
                String before= "https://ui-avatars.com/api/?name=";
                String after="&background=455a64&color=ffffff";
                announcement.setImageUrl(before+announcement.getTitle()+after);
            }
        }
        announcement.setAdminImageUrl("https://lww-buck2.oss-cn-beijing.aliyuncs.com/quick/admin.jpeg");
        boolean success = announcementService.addAnnouncement(announcement);
        if (success) {
            List<Announcement> announcements = announcementService.getAllAnnouncements();
            redisUtils.setObject("quickapi:announcements", announcements);
            return Result.success();
        } else {
            return Result.error("Failed to add announcement");
        }
    }



    @GetMapping("/list")
    @Operation(summary = "用户获取所有可见公告")
    public Result getAllAnnouncements() {
//        缓存里面存在,之间从缓存中获取
        if (redisUtils.keyExists("quickapi:announcements"))
        {
            String string = stringRedisTemplate.opsForValue().get("quickapi:announcements");
            List<Announcement> announcements = JSON.parseObject(string, new TypeReference<List<Announcement>>() {});
            return Result.success(announcements,"从缓存中获取");

        }

//缓存里面不存在，查询数据库，并将结果写入缓存
        List<Announcement> announcements = announcementService.getAllAnnouncements();
        if (announcements != null) {
            String jsonString = JSON.toJSONString(announcements);
            stringRedisTemplate.opsForValue().set("quickapi:announcements", jsonString);
            return Result.success(announcements,"从mysql数据库中获取");
        } else {
            return Result.error("Failed to load announcements");
        }
    }



    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除公告")
    public Result deleteAnnouncement(@PathVariable int id) {
        boolean success = announcementService.deleteAnnouncementById(id);
        if (success) {
            return Result.success();
        } else {
            return Result.error("Failed to delete announcement");
        }
    }

    @PutMapping("/update")
    @Operation(summary = "更新公告")
    public Result updateAnnouncement(@RequestBody Announcement announcement) {
        boolean success = announcementService.updateAnnouncement(announcement);
        if (success) {
            return Result.success(announcement);
        } else {
            return Result.error("Failed to update announcement");
        }
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "获取一个公告")
    public Result getAnnouncementById(@PathVariable int id) {
        Announcement announcement = announcementService.getAnnouncementById(id);
        if (announcement != null) {
            return Result.success(announcement);
        } else {
            return Result.error("Failed to find the announcement");
        }
    }









    @GetMapping("/adminlist")
    @Operation(summary = "管理员获取所有公告")
    public Result getAllAnnouncements2() {
        List<Announcement> announcements = announcementService.getAllAnnouncements2();
        if (announcements != null) {
            return Result.success(announcements);
        } else {
            return Result.error("Failed to load announcements");
        }
    }

}
