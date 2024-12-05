package com.upc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.upc.entity.Admin;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lww
 * @since 2024-07-20
 */
public interface IAdminService  {
    public Admin getById(Integer id);

}
