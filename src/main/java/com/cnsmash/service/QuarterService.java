package com.cnsmash.service;

import com.cnsmash.pojo.entity.Quarter;
import com.cnsmash.pojo.ro.AddQuarterRo;
import com.cnsmash.pojo.ro.CustomQuarterAttendRo;
import com.cnsmash.pojo.ro.CustomQuarterListRo;

import java.util.List;

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

    /**
     * 查询自定义赛季列表
     */
    List<Quarter> getList(CustomQuarterListRo ro);

    List<Quarter> getAttendList(CustomQuarterListRo ro, Long userId);

    /**
     * 报名
     * @return
     */
    String signinQuarter(CustomQuarterAttendRo ro, Long userId);

    /**
     * 修改
     */
    String signinUpdate(CustomQuarterAttendRo ro, Long userId);

    /**
     * 退出
     */
    String signout(CustomQuarterAttendRo ro, Long userId);

    /**
     * 主动开启赛季
     */
    String hostStartQuarter(Long quarterId, Long host);

    void quarterSumup();
}
