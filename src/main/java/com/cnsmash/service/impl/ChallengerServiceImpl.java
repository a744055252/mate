package com.cnsmash.service.impl;

import com.cnsmash.mapper.ChallengerMapper;
import com.cnsmash.pojo.vo.ChallengerVo;
import com.cnsmash.service.ChallengerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChallengerServiceImpl implements ChallengerService {

    @Autowired
    ChallengerMapper challengerMapper;

    @Override
    public ChallengerVo getCurrentChallenger() {
        return challengerMapper.getCurrentChallenger();
    }

}
