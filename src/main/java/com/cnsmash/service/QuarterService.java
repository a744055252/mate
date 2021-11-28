package com.cnsmash.service;

import com.cnsmash.pojo.entity.Quarter;
import com.cnsmash.pojo.ro.AddQuarterRo;

/**
 * @author guanhuan_li
 */
public interface QuarterService {

    /**
     * 获取当前赛季
     * @return 赛季
     */
    Quarter getCurrent();

    /**
     * 新增赛季
     * @param ro 赛季信息
     */
    void add(AddQuarterRo ro);
}
