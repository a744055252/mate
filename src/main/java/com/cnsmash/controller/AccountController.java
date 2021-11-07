package com.cnsmash.controller;

import com.cnsmash.config.login.pojo.LoginUser;
import com.cnsmash.pojo.entity.Account;
import com.cnsmash.pojo.entity.ReposResult;
import com.cnsmash.pojo.entity.User;
import com.cnsmash.pojo.ro.RegisterUserRo;
import com.cnsmash.pojo.vo.UserInfo;
import com.cnsmash.service.AccountService;
import com.cnsmash.util.MateAuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @author guanhuan_li
 */
@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping("/register")
    public ReposResult<User> register(@RequestBody @Valid RegisterUserRo ro) {
        return ReposResult.ok(accountService.register(ro));
    }

    @GetMapping
    public ReposResult<UserInfo> info(){
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        return ReposResult.ok(accountService.info(loginUser.getId()));
    }

}
