package com.cnsmash.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cnsmash.match.MatchBean;
import com.cnsmash.pojo.bean.Room;
import com.cnsmash.pojo.ro.BattleResultRo;
import com.cnsmash.pojo.ro.CreateRoomRo;
import com.cnsmash.pojo.ro.FindRoomRo;
import com.cnsmash.pojo.ro.PageBattleRo;
import com.cnsmash.pojo.ro.StopBattleRo;
import com.cnsmash.pojo.ro.SubmitFighterRo;
import com.cnsmash.pojo.vo.MatchResultVo;
import com.cnsmash.pojo.vo.PageBattleVo;
import com.cnsmash.pojo.vo.UserBattleStatusVo;

import java.util.List;
import java.util.Map;

/**
 * 对战
 * @author guanhuan_li
 */
public interface BattleService {

    /**
     * 开始匹配
     * @param userId 用户id
     * @return 匹配结果
     */
    MatchResultVo match(Long userId);

    /**
     * 获取用户匹配状态
     * @param userId 用户id
     * @return 结果
     */
    UserBattleStatusVo userBattleStatus(Long userId);

    /**
     * 取消匹配
     * @param userId 用户id
     */
    void cancel(Long userId);

    /**
     * 获取对战详情
     * @param battleId 对战id
     * @return 详情
     */
    MatchResultVo get(Long battleId);

    /**
     * 创建房间
     * @param userId 用户id
     * @param ro 创建请求
     */
    void createRoom(Long userId, CreateRoomRo ro);

    /**
     * 提交比赛结果
     * @param userId 用户id
     * @param ro 请求
     */
    void submitBattleResult(Long userId, BattleResultRo ro);

    /**
     * 查询对战
     * @param ro 查询条件
     * @return 分页数据
     */
    Page<PageBattleVo> pageBattle(PageBattleRo ro);

    /**
     * 获取等待匹配列表
     * @return 在匹配用户
     */
    Map<Long, MatchBean> allWaitMatch();

    /**
     * 获取等待匹配的用户
     * @return 在匹配用户
     */
    MatchBean waitMatch(Long userId);

    /**
     * 中止对战
     * @param ro 请求
     */
    void stop(Long userId, StopBattleRo ro);

    /**
     * 提交对手斗士结果
     * @param userId 用户id
     * @param ro 请求
     */
    void submitFighter(Long userId, SubmitFighterRo ro);

    /**
     * 查询双方对战次数
     * @param userId1
     * @param userId2
     * @return
     */
    Long getHead2HeadCount(Long userId1, Long userId2);

    /**
     * 查询报分冲突列表
     * @param userId 用户ID
     * @return 冲突报分比赛ID列表
     */
    List<Long> getConflict(Long userId);
}
