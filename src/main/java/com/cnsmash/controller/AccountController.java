package com.cnsmash.controller;

import com.cnsmash.pojo.entity.Account;
import com.cnsmash.pojo.entity.ReposResult;
import com.cnsmash.service.impl.AccountServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guanhuan_li
 */
@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountServiceImpl accountServiceImpl;

    @GetMapping
    public ReposResult<Account> list(){
        return ReposResult.ok();
    }

}
