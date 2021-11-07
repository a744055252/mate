package com.cnsmash.controller;

import com.cnsmash.pojo.entity.Account;
import com.cnsmash.pojo.entity.ReposResult;
import com.cnsmash.pojo.ro.RegisterUserRo;
import com.cnsmash.service.AccountService;
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
    public ReposResult<Void> register(@RequestBody @Valid RegisterUserRo ro) {
        accountService.register(ro);
        return ReposResult.ok();

    }

    @GetMapping
    public ReposResult<Account> list(){
        return ReposResult.ok();
    }

}
