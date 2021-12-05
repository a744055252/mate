package com.cnsmash.controller;

import com.cnsmash.config.login.pojo.LoginUser;
import com.cnsmash.pojo.bean.ReposResult;
import com.cnsmash.pojo.vo.MyRankVo;
import com.cnsmash.service.RankService;
import com.cnsmash.util.MateAuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guanhuan_li
 */
@RestController
@RequestMapping("/rank")
public class RankController {

    @Autowired
    RankService rankService;

    @GetMapping
    public ReposResult<MyRankVo> myRank(){
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        return ReposResult.ok(rankService.userRank(loginUser.getUserId()));
    }

}
