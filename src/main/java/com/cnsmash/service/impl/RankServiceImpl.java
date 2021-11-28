package com.cnsmash.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cnsmash.config.login.pojo.LoginUser;
import com.cnsmash.mapper.UserRankMapper;
import com.cnsmash.pojo.entity.Quarter;
import com.cnsmash.pojo.entity.UserRank;
import com.cnsmash.pojo.vo.MyRankVo;
import com.cnsmash.service.QuarterService;
import com.cnsmash.service.RankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author guanhuan_li
 */
@Slf4j
@Service
public class RankServiceImpl implements RankService {

    @Autowired
    UserRankMapper userRankMapper;

    @Autowired
    QuarterService quarterService;

    @Override
    public MyRankVo myRank(LoginUser loginUser) {
        Quarter quarter = quarterService.getCurrent();
        String quarterCode = quarter.getCode();
        UserRank userRank = get(loginUser.getUserId(), quarterCode);
        MyRankVo vo = new MyRankVo(loginUser.getUserId(), quarterCode);
        if (userRank == null) {
            return vo;
        }
        BeanUtils.copyProperties(userRank, vo);
        return vo;
    }

    private UserRank get(Long userId, String quarter) {
        QueryWrapper<UserRank> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                .eq("quarter",quarter);
        return userRankMapper.selectOne(queryWrapper);
    }
}
