package com.upc.service.impl;

import com.upc.entity.Admin;
import com.upc.mapper.AdminMapper;
import com.upc.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lww
 * @since 2024-07-20
 */
@Service
public class AdminServiceImpl  implements IAdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin getById(Integer id) {
        Admin admin=adminMapper.selectById(id);
        return admin;
    }
}
