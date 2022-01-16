package com.cnsmash.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cnsmash.pojo.BattleResultType;
import com.cnsmash.pojo.entity.UserRank;
import com.cnsmash.pojo.ro.PageFighterRo;
import com.cnsmash.pojo.vo.MyRankVo;
import com.cnsmash.pojo.vo.UserDetail;

/**
 * @author guanhuan_li
 */
public interface RankService {

    /**
     * 我的排位
     * @param userId 登录人
     * @return 排位数据
     */
    MyRankVo userRank(Long userId);

    /**
     * 提交结果
     * @param userId 用户
     * @param type 类型
     * @param change 变化
     */
    void submitRank(Long userId, BattleResultType type, Long change);

    /**
     * 分页查看角色排名
     * @param ro 请求
     * @return
     */
    Page<UserDetail> page(PageFighterRo ro);

    /**
     * 初始化赛季排名
     * @param userId 用户id
     * @param quarter
     * @return
     */
    UserRank initQuarterRank(Long userId, String quarter);

    /**
     * 获取用户排位分
     * @param userId 用户id
     * @param quarter 赛季
     * @return 分数
     */
    UserRank get(Long userId, String quarter);
}
