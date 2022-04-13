package com.cnsmash.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cnsmash.pojo.entity.Tournament;
import com.cnsmash.pojo.entity.UserRank;
import com.cnsmash.pojo.ro.PageFighterRo;
import com.cnsmash.pojo.ro.PageTournamentRo;
import com.cnsmash.pojo.vo.TournamentDetailVo;
import com.cnsmash.pojo.vo.TournamentThumbnailVo;
import com.cnsmash.pojo.vo.UserThumbnailVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Amaki
 */

@Repository
public interface TournamentMapper extends BaseMapper<Tournament> {

    /**
     * 分页查询比赛列表
     * @param page 分页
     * @param ro 查询条件
     * @return 结果
     */
    Page<TournamentThumbnailVo> pageTournament(IPage<UserRank> page, @Param("ro") PageTournamentRo ro);

    /**
     * 查询比赛详情
     * @param id 比赛ID
     */
    TournamentDetailVo tournamentDetail(Long id);

    /**
     * 查询比赛报名列表
     * @param id 比赛ID
     */
    List<UserThumbnailVo> playerList(Long id);

}
