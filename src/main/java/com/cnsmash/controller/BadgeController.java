package com.cnsmash.controller;

import com.cnsmash.config.login.pojo.LoginUser;
import com.cnsmash.pojo.bean.ReposResult;
import com.cnsmash.pojo.entity.Badge;
import com.cnsmash.service.BadgeService;
import com.cnsmash.util.MateAuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Toddhead
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/badge")
public class BadgeController {

    @Autowired
    BadgeService badgeService;

    /**
     * 获取全徽章列表
     */
    @GetMapping("/fullList")
    public ReposResult<List<Badge>> badgeList() {
        return ReposResult.ok(badgeService.getFullList());
    }

    @GetMapping
    public ReposResult<List<Badge>> myBadgeList() {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        return ReposResult.ok(badgeService.getMyBadgeList(loginUser.getUserId()));
    }

    @GetMapping("/wearBadge")
    public ReposResult<String> wearBadge(@RequestParam Long badgeId) {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        return ReposResult.ok(badgeService.wearBadge(badgeId, loginUser.getUserId()));
    }

    @GetMapping("/gacha")
    public ReposResult<List<Badge>> gacha() {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        return ReposResult.ok(badgeService.gacha(loginUser.getUserId(), 1));
    }

    @GetMapping("/gachaJiuRen")
    public ReposResult<List<Badge>> gachaJiuRen() {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        return ReposResult.ok(badgeService.gacha(loginUser.getUserId(), 11));
    }

}
