package com.cnsmash.mapper;

import com.cnsmash.pojo.vo.ChallengerVo;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengerMapper {

    /**
     * 查询当前挑战者
     * @return
     */
    ChallengerVo getCurrentChallenger();

}
