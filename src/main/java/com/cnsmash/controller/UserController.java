package com.cnsmash.controller;

import com.cnsmash.config.login.pojo.LoginUser;
import com.cnsmash.pojo.bean.ReposResult;
import com.cnsmash.pojo.entity.User;
import com.cnsmash.pojo.ro.UpdateMatchRuleRo;
import com.cnsmash.service.UserService;
import com.cnsmash.util.MateAuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ReposResult<User> get() {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        return ReposResult.ok(userService.getById(loginUser.getUserId()));
    }

    @PostMapping("/rule")
    public ReposResult<Void> updateMatchRule(@RequestBody @Valid UpdateMatchRuleRo ro) {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        userService.updateMatchRule(loginUser.getUserId(), ro);
        return ReposResult.ok();
    }

    @GetMapping("/id")
    public ReposResult<User> getById(@RequestParam Long userId){
        return ReposResult.ok(userService.getById(userId));
    }

}
