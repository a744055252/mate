package com.cnsmash.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cnsmash.pojo.dto.PlayerQuarterFighterDto;
import com.cnsmash.pojo.entity.GameFighter;
import com.cnsmash.pojo.entity.UserFighter;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author guanhuan_li
 */
@Repository
public interface GameFighterMapper extends BaseMapper<GameFighter> {

    List<UserFighter> getQuarterUserFighter(String quarter);

    List<PlayerQuarterFighterDto> getPlayerQuarterFighter(String quarter);

}
