package com.upc.controller.admin;


import com.upc.common.TestComponent;
import com.upc.entity.Admin;
import com.upc.service.IAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lww
 * @since 2024-07-20
 */
@RestController
@RequestMapping("/admin")
@Tag(name = "admin管理")
public class AdminController {
    @Autowired
    IAdminService adminService;
    @Autowired
    TestComponent testComponent;



    @GetMapping("/test")
    @Operation(summary = "admin")
    public String test()
    {
        Integer id=1;
        Admin admin=adminService.getById(id);
        System.out.println(admin);
        return "test";
    }


}
