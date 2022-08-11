package com.cnsmash.controller;

import com.cnsmash.pojo.bean.ReposResult;
import com.cnsmash.pojo.vo.ChallengerVo;
import com.cnsmash.service.ChallengerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/challenger")
public class ChallengerController {

    @Autowired
    ChallengerService challengerService;

    @GetMapping
    public ReposResult<ChallengerVo> getChallenger() {
        return ReposResult.ok(challengerService.getCurrentChallenger());
    }

}
