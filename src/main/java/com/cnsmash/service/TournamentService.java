package com.cnsmash.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cnsmash.pojo.entity.TournamentPlayer;
import com.cnsmash.pojo.ro.*;
import com.cnsmash.pojo.vo.*;

import java.util.List;

/**
 * @author Amaki
 */
public interface TournamentService {

    /**
     * 添加比赛
     * @param host 举办人
     * @param ro 比赛详情
     */
    void addTournament(Long host, AddTournamentRo ro);

    /**
     * 分页查看比赛列表
     * @param ro 请求
     */
    Page<TournamentThumbnailVo> page(PageTournamentRo ro);

    /**
     * 查询比赛详情
     * @param id 比赛ID
     */
    TournamentDetailVo detail(Long id);

    /**
     * 查询比赛选手报名列表
     * @param id 比赛ID
     */
    List<TournamentPlayerVo> playerList(Long id);

    /**
     * 判断是否已经报名
     * @param playerId 玩家ID
     * @param tournamentId 比赛ID
     */
    TournamentPlayer isRegister(Long playerId, Long tournamentId);

    /**
     * 报名比赛
     * @param playerId 玩家ID
     * @param ro 报名信息
     */
    String register(Long playerId, RegisterTournamentRo ro);

    /**
     * 更新比赛报名信息
     * @param playerId 玩家ID
     * @param ro 报名信息
     */
    String updateRegister(Long playerId, RegisterTournamentRo ro);

    /**
     * 取消报名比赛
     * @param playerId 玩家ID
     * @param tournamentId 比赛ID
     */
    String unregister(Long playerId, Long tournamentId);

    /**
     * 自动开始比赛
     * @param tournamentId 比赛ID
     */
    void startToutnament(Long tournamentId);

    /**
     * 手动开始比赛
     * @param tournamentId 比赛ID
     * @param playerId 主办人ID
     */
    String startTournament(Long tournamentId, Long playerId);

    /**
     * 更新比赛信息
     * @param host 主办人ID
     * @param ro 比赛信息
     */
    String updateTournament(Long host, AddTournamentRo ro);

    /**
     * 获取对局列表
     * @param tournamentId 比赛ID
     */
    List<TournamentSetVo> setList(Long tournamentId);

    /**
     * 报告分数
     * @param tournamentSetId 对局ID
     * @param playerId 玩家ID
     */
    String report(Long tournamentSetId, Long playerId);

    /**
     * 创建房间
     * @param ro 请求
     */
    void room(TournamentRoomRo ro);

    /**
     * 标记/取消标记比赛
     * @param ro 请求
     */
    void focus(TournamentSetFocusRo ro);

    /**
     * 获取玩家比赛成绩列表
     * @param id 选手ID
     * @return 列表
     */
    List<TournamentResultVo> playerResultList(Long id, Integer limit);

    /**
     * 按种子位查询比赛选手报名列表
     * @param id 比赛ID
     */
    List<TournamentPlayerVo> seedList(Long id);

    /**
     * 更新种子排位
     * @param seeding 选手种子位列表
     * @param playerId 修改人ID
     * @return
     */
    String updateSeeing(TournamentSeedingRo seeding, Long playerId);

    /**
     * 发布比赛对阵图
     * @param tournamentId 比赛ID
     * @param playerId 主办人ID
     */
    String publishTournament(Long tournamentId, Long playerId);


}
