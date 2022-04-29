package com.cnsmash.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cnsmash.mapper.FreeroomMapper;
import com.cnsmash.pojo.FreeroomStatus;
import com.cnsmash.pojo.entity.Freeroom;
import com.cnsmash.pojo.ro.AddFreeroomRo;
import com.cnsmash.pojo.ro.ListFreeroomRo;
import com.cnsmash.pojo.vo.FreeroomThumbnailVo;
import com.cnsmash.pojo.vo.FreeroomVo;
import com.cnsmash.service.FreeroomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Toddhead
 */
@Slf4j
@Service
public class FreeroomServiceImpl implements FreeroomService {

    @Autowired
    FreeroomMapper freeroomMapper;

    @Override
    public void add(AddFreeroomRo ro, Long host) {
        // 关闭host其他房间
        freeroomMapper.updatePlayerRoomShutdown(host);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireTime = now.plusHours(1);

        Freeroom freeroom = new Freeroom();
        BeanUtils.copyProperties(ro, freeroom);
        freeroom.setHost(host);
        freeroom.setCreateTime(Timestamp.valueOf(now));
        freeroom.setUpdateTime(Timestamp.valueOf(now));
        freeroom.setExpireTime(Timestamp.valueOf(expireTime));
        freeroom.setStatus(FreeroomStatus.active);
        freeroomMapper.insert(freeroom);
    }

    @Override
    public void update(AddFreeroomRo ro, Long host) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireTime = now.plusHours(1);

        Freeroom freeroom = freeroomMapper.selectById(ro.getId());
        BeanUtils.copyProperties(ro, freeroom);
        freeroom.setUpdateTime(Timestamp.valueOf(now));
        freeroom.setExpireTime(Timestamp.valueOf(expireTime));
        freeroom.setStatus(FreeroomStatus.active);
        freeroomMapper.updateById(freeroom);
    }

    @Override
    public List<FreeroomVo> list(ListFreeroomRo ro) {
        return freeroomMapper.list(ro.getServer(), ro.getMaximum());
    }

    @Override
    public FreeroomVo detail(Long id) {
        return freeroomMapper.detail(id);
    }

    @Override
    public FreeroomVo detailByUser(Long id) {
        return freeroomMapper.detailByUser(id);
    }

    @Override
    public void shutdownRoomByPlayer(Long id) {
        freeroomMapper.updatePlayerRoomShutdown(id);
    }


    /********** 定时任务 **********/
    @Scheduled(cron = "0 */5 * * * ?")
    public void updateRoom() {
        log.info("更新房间状态！------start------");
        freeroomMapper.updateRoom();
        log.info("更新房间状态！------end------");
    }
}
