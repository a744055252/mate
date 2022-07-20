package com.cnsmash.controller;

import com.cnsmash.config.login.pojo.LoginUser;
import com.cnsmash.pojo.bean.ReposResult;
import com.cnsmash.pojo.entity.User;
import com.cnsmash.pojo.ro.AddUserRo;
import com.cnsmash.pojo.ro.UpdateMatchRuleRo;
import com.cnsmash.pojo.vo.HistoryRecordVo;
import com.cnsmash.pojo.vo.RuleVo;
import com.cnsmash.pojo.vo.UserDetail;
import com.cnsmash.service.UserService;
import com.cnsmash.util.MateAuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author guanhuan_li
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ReposResult<UserDetail> get() {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        return ReposResult.ok(userService.getDetailById(loginUser.getUserId()));
    }

    @PostMapping
    public ReposResult<Void> add(@RequestBody @Valid AddUserRo ro) {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        ro.setAccountId(loginUser.getId());
        userService.add(ro);
        return ReposResult.ok();
    }

    @PostMapping("/rule")
    public ReposResult<Void> updateMatchRule(@RequestBody @Valid UpdateMatchRuleRo ro) {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        userService.updateMatchRule(loginUser.getUserId(), ro);
        return ReposResult.ok();
    }

    @GetMapping("/rule")
    public ReposResult<RuleVo> getMyRule() {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        return ReposResult.ok(userService.getMyRule(loginUser.getUserId()));
    }

    @GetMapping("/id")
    public ReposResult<UserDetail> getById(@RequestParam Long userId){
        return ReposResult.ok(userService.getDetailById(userId));
    }

    @GetMapping("/detail")
    public ReposResult<UserDetail> getDetailById() {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        return ReposResult.ok(userService.getUserDetail(loginUser.getUserId()));
    }

    @GetMapping("/userDetail")
    public ReposResult<UserDetail> getDetailById(@RequestParam Long userId) {
        return ReposResult.ok(userService.getUserDetail(userId));
    }

    @GetMapping("/fighter")
    public ReposResult<List<String>> getUserFighter(@RequestParam Long userId) {
        return ReposResult.ok(userService.getFighterById(userId));
    }

    @GetMapping("/recountFighter")
    public ReposResult<String> recountFighter() {
        userService.recountFighter();
        return ReposResult.ok("finish");
    }



}
