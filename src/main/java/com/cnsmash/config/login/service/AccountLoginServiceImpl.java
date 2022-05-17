package com.cnsmash.config.login.service;

import com.cnsmash.config.login.pojo.*;
import com.cnsmash.pojo.LoginAuth;
import com.cnsmash.mapper.UploadFileMapper;
import com.cnsmash.pojo.entity.Account;
import com.cnsmash.pojo.entity.UploadFile;
import com.cnsmash.pojo.entity.User;
import com.cnsmash.service.AccountService;
import com.cnsmash.service.UserService;
import com.cnsmash.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpSession;
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

    @Autowired
    UserService userService;

    @Autowired
    UploadFileMapper uploadFileMapper;

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

        List<User> users = userService.listByAccountId(account.getId());
        if (CollectionUtils.isEmpty(users)) {
            throw new BadCredentialsException("身份不存在！");
        }

        HttpSession httpSession = ro.getDetails().getHttpSession();
        String authJson = (String) httpSession.getAttribute(AccountService.LOGIN_AUTH_KEY);
        if (StringUtils.isNoneBlank(authJson)) {
            LoginAuth loginAuth = JsonUtil.parseJson(authJson, new TypeReference<LoginAuth>() {
            });
            // 如果有微信id更新
            if (loginAuth.getWxUserId() != null) {
                account.setMappingId(loginAuth.getWxUserId());
                accountService.update(account);
            }
        }


        User user = users.get(0);

        // 头像
        UploadFile uploadFile = uploadFileMapper.selectById(user.getHead());

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
        loginUser.setUserId(user.getId());
        loginUser.setPassword(account.getPassword());
        loginUser.setUsername(username);
        loginUser.setNickName(user.getNickName());
        // 如果有头像就保存头像信息
        if (uploadFile != null) {
            loginUser.setHeadSrc(uploadFile.getSrc());
        }
        return loginUser;
    }
}
