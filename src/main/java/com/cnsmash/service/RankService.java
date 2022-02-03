package com.cnsmash.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cnsmash.pojo.BattleResultType;
import com.cnsmash.pojo.RankChangeType;
import com.cnsmash.pojo.entity.UserRank;
import com.cnsmash.pojo.ro.PageFighterRo;
import com.cnsmash.pojo.vo.MyRankVo;
import com.cnsmash.pojo.vo.UserDetail;

import java.util.Collection;
import java.util.List;

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
     * @param battleId 对战id
     * @param userId 用户
     * @param type 类型
     * @param change 变化
     */
    void submitRank(Long battleId, Long userId, BattleResultType type, Long change);

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

    /**
     * 添加用户分数
     * @param quarter 赛季
     * @param userId 用户id
     * @param type 分数变化类型
     * @param change 变化量
     * @param logKey 唯一主键
     */
    void add(String quarter, Long userId, RankChangeType type, Long change, String logKey);

    /**
     * 获取所有用户排名
     * @param quarter 赛季
     * @return 用户排位
     */
    List<UserRank> listAll(String quarter);

    /**
     * 批量更新
     * @param userRanks 用户
     * @return 结果
     */
    boolean updateBatchById(Collection<UserRank> userRanks);
}
