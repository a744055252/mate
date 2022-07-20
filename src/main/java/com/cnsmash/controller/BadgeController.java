package com.cnsmash.controller;

import com.cnsmash.pojo.bean.ReposResult;
import com.cnsmash.pojo.entity.Badge;
import com.cnsmash.service.BadgeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping
    public ReposResult<List<Badge>> badgeList() {
        return ReposResult.ok(badgeService.getFullList());
    }

//    @GetMapping("/gacha")
//    public ReposResult<Badge> gacha() {
//
//    }

}
