package com.cnsmash.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cnsmash.exception.CodeException;
import com.cnsmash.exception.ErrorCode;
import com.cnsmash.mapper.QuarterMapper;
import com.cnsmash.pojo.entity.Quarter;
import com.cnsmash.pojo.ro.AddQuarterRo;
import com.cnsmash.service.QuarterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 赛季
 * @author guanhuan_li
 */
@Slf4j
@Service
public class QuarterServiceImpl implements QuarterService {

    @Autowired
    QuarterMapper quarterMapper;

    @Override
    public Quarter getCurrent() {
        QueryWrapper<Quarter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("current", true);
        return quarterMapper.selectOne(queryWrapper);
    }

    @Override
    public void add(AddQuarterRo ro) {

        Quarter codeQuarter = getByCode(ro.getCode());
        if (codeQuarter != null) {
            throw new CodeException(ErrorCode.NICKNAME_EXIT, "赛季编码已存在");
        }

        Quarter quarter = new Quarter();
        BeanUtils.copyProperties(ro, quarter);
        quarter.setCurrent(true);
        // 过期当前赛季
        invalidCurrentQuarter();
        quarterMapper.insert(quarter);
    }

    /**
     * 标记当前赛季过时
     */
    private void invalidCurrentQuarter() {
        QueryWrapper<Quarter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("current", true);

        Quarter quarter = new Quarter();
        quarter.setCurrent(false);

        quarterMapper.update(quarter, queryWrapper);
    }

    private Quarter getByCode(String code) {
        QueryWrapper<Quarter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code);
        return quarterMapper.selectOne(queryWrapper);
    }
}
