package com.upc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.upc.entity.Announcement;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lww
 * @since 2024-07-20
 */
public interface IAnnouncementService  {
    boolean addAnnouncement(Announcement announcement);
    boolean deleteAnnouncementById(int id);
    boolean updateAnnouncement(Announcement announcement);
    Announcement getAnnouncementById(int id);
    List<Announcement> getAllAnnouncements();

    List<Announcement> getAllAnnouncements2();
}
