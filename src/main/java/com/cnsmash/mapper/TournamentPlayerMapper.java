package com.cnsmash.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cnsmash.pojo.entity.TournamentPlayer;
import com.cnsmash.pojo.vo.TournamentResultVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Toddhead
 */
@Repository
public interface TournamentPlayerMapper extends BaseMapper<TournamentPlayer> {

    /**
     * 更新比赛结果
     * @param tournamentId 比赛ID
     * @param playerId 选手ID
     * @param result 结果排名
     */
    void updateResult(Long tournamentId, Long playerId, Integer result);

    /**
     * 查询选手比赛结果列表
     * @param id 选手ID
     * @param limit 数量限制
     * @return 结果列表
     */
    List<TournamentResultVo> getPlayerResultList(Long id, Integer limit);

}
