package com.cnsmash.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cnsmash.mapper.BadgeMapper;
import com.cnsmash.pojo.entity.Badge;
import com.cnsmash.service.BadgeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Toddhead
 */
@Slf4j
@Service
public class BadgeServiceImpl implements BadgeService {

    @Autowired
    BadgeMapper badgeMapper;

    public List<Badge> getFullList() {
        QueryWrapper<Badge> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("`order`");
        return badgeMapper.selectList(wrapper);
    }
}
