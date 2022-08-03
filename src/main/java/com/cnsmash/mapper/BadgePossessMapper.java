package com.cnsmash.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cnsmash.pojo.entity.Badge;
import com.cnsmash.pojo.entity.BadgePossess;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BadgePossessMapper extends BaseMapper<BadgePossess> {

    List<Badge> getUserBadgeList(Long userId);

}
