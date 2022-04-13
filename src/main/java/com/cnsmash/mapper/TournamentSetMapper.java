package com.cnsmash.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cnsmash.pojo.entity.TournamentSet;
import com.cnsmash.pojo.vo.TournamentSetVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Amaki
 */

@Repository
public interface TournamentSetMapper extends BaseMapper<TournamentSet> {

    /**
     * 获取比赛对局列表
     * @param id 比赛ID
     * @return 对局列表
     */
    List<TournamentSetVo> setList(@Param("id") Long id);

}
