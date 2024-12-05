package com.upc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.upc.entity.Announcement;
import com.upc.mapper.AnnouncementMapper;
import com.upc.service.IAnnouncementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lww
 * @since 2024-07-20
 */
@Service
public class AnnouncementServiceImpl implements IAnnouncementService {
    @Autowired
    private AnnouncementMapper announcementMapper;

    @Override
    public boolean addAnnouncement(Announcement announcement) {
        announcement.setCreateTime(LocalDateTime.now());
        return announcementMapper.insert(announcement) > 0;
    }

    @Override
    public boolean deleteAnnouncementById(int id) {
        return announcementMapper.deleteById(id) > 0;
    }

    @Override
    public boolean updateAnnouncement(Announcement announcement) {
        return announcementMapper.updateById(announcement) > 0;
    }

    @Override
    public Announcement getAnnouncementById(int id) {
        return announcementMapper.selectById(id);
    }

    @Override
    public List<Announcement> getAllAnnouncements() {
        LambdaQueryWrapper
                <Announcement> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Announcement::getVisible,1);
        return announcementMapper.selectList(queryWrapper);
    }

    @Override
    public List<Announcement> getAllAnnouncements2() {
        return announcementMapper.selectList(null);
    }

}
