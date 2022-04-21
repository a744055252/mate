package com.cnsmash.controller;

import com.cnsmash.config.login.pojo.LoginUser;
import com.cnsmash.pojo.LoginAuth;
import com.cnsmash.pojo.bean.ReposResult;
import com.cnsmash.pojo.entity.User;
import com.cnsmash.pojo.ro.AccountUserRo;
import com.cnsmash.pojo.ro.RegisterUserRo;
import com.cnsmash.pojo.ro.UpdatePasswordRo;
import com.cnsmash.pojo.ro.UpdateUserInfoRo;
import com.cnsmash.pojo.vo.AccountUserVo;
import com.cnsmash.pojo.vo.UserInfo;
import com.cnsmash.service.AccountService;
import com.cnsmash.util.JsonUtil;
import com.cnsmash.util.MateAuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


/**
 * @author guanhuan_li
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping("/user")
    public ReposResult<AccountUserVo> user(@RequestBody @Valid AccountUserRo ro, HttpSession session) {
        AccountUserVo vo = accountService.user(ro);
        session.setAttribute(AccountService.LOGIN_AUTH_KEY, JsonUtil.toJson(new LoginAuth(vo)));
        return ReposResult.ok(vo);
    }

    @PostMapping("/wechat")
    public ReposResult<AccountUserVo> wechat(@RequestParam String code, HttpSession session) {
        AccountUserVo vo = accountService.wxUser(code);
        session.setAttribute(AccountService.LOGIN_AUTH_KEY, JsonUtil.toJson(new LoginAuth(vo)));
        return ReposResult.ok(vo);
    }

    @PostMapping("/register")
    public ReposResult<User> register(@RequestBody @Valid RegisterUserRo ro) {
        return ReposResult.ok(accountService.register(ro));
    }

    @GetMapping
    public ReposResult<UserInfo> info(){
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        return ReposResult.ok(accountService.info(loginUser.getId()));
    }

    @PostMapping("/password")
    public ReposResult<Void> updatePassword(@RequestBody @Valid UpdatePasswordRo ro) {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        accountService.updatePassword(loginUser, ro);
        return ReposResult.ok();
    }

    @PostMapping("/userInfo")
    public ReposResult<Void> updateUserInfo(@RequestBody @Valid UpdateUserInfoRo ro) {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        accountService.updateUserInfo(loginUser, ro);
        return ReposResult.ok();
    }

    @PostMapping("/switchUser")
    public ReposResult<AccountUserVo> switchUser(HttpSession session) {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        AccountUserVo vo = accountService.switchUser(loginUser.getId());
        session.setAttribute(AccountService.LOGIN_AUTH_KEY, JsonUtil.toJson(new LoginAuth(vo)));
        return ReposResult.ok(vo);
    }
}
