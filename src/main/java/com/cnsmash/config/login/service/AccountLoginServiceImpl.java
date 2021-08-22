package com.cnsmash.config.login.service;

import com.cnsmash.config.login.pojo.*;
import com.cnsmash.pojo.entity.Account;
import com.cnsmash.service.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guanhuan_li
 * #date  2020/12/2 15:22
 */
@Component
public class AccountLoginServiceImpl implements LoginUserService {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AccountService accountService;

    @Override
    public LoginUserVo getLoginUserVo(LoginUser loginUser) {
        LoginUserVo vo = new LoginUserVo();
        BeanUtils.copyProperties(loginUser, vo);
        return vo;
    }

    @Override
    public LoginType getLoginType() {
        return LoginType.account;
    }

    @Override
    public PasswordEncoder getPasswordEncoder() {
        return encoder;
    }

    @Override
    public LoginUser loadUser(LoginUserRo ro) throws UsernameNotFoundException {

        String username = ro.getUsername();

        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("用户名不能为空！");
        }

        Account account = accountService.login(username);
        if (account == null) {
            // 账号不存在
            throw new BadCredentialsException("账号不存在！");
        }

        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(account, loginUser);
        loginUser.setAccountNonLocked(true);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ACCOUNT"));
        loginUser.setAuthorities(authorities);
        loginUser.setLoginType(getLoginType());
        loginUser.setRoleType(RoleType.user);
        loginUser.setCredentialsNonExpired(true);
        loginUser.setAccountNonExpired(true);
        loginUser.setEnabled(true);
        loginUser.setId(account.getId());
        loginUser.setPassword(account.getPassword());
        loginUser.setUsername(username);
        return loginUser;
    }
}
