package com.cnsmash.config.login;

import com.cnsmash.config.login.pojo.LoginType;
import com.cnsmash.config.login.pojo.LoginUserRo;
import com.cnsmash.config.login.service.LoginUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author guanhuan_li
 * #date 2020/8/26 14:43
 */
@Component
@Slf4j
public class MateAuthenticationProvider implements AuthenticationProvider, ApplicationContextAware {


    private ApplicationContext applicationContext;

    private static Map<LoginType, LoginUserService> type2service;

    @PostConstruct
    public void init(){
        type2service = applicationContext.getBeansOfType(LoginUserService.class).values().stream()
                .collect(Collectors.toMap(LoginUserService::getLoginType, Function.identity()));
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 这个获取表单输入中返回的用户名;
        String userName = authentication.getName();
        // 这个是表单中输入的密码；
        String password = (String) authentication.getCredentials();

        MateAuthenticationDetailsSource.MateWebAuthenticationDetails details = (MateAuthenticationDetailsSource.MateWebAuthenticationDetails) authentication.getDetails();

        LoginType loginType = details.getLoginType();
        log.info("用户：【"+userName+"】登录类型：【"+loginType+"】");

        UserDetails userInfo;
        LoginUserRo loginUserRo = new LoginUserRo(userName, details);
        LoginUserService loginUserService = type2service.get(loginType);
        if (loginUserService != null) {
            try {
                userInfo = loginUserService.loadUser(loginUserRo);
            } catch (Exception e) {
                throw new AuthenticationServiceException(e.getMessage());
            }
            PasswordEncoder encoder = loginUserService.getPasswordEncoder();
            if (!encoder.matches(password, userInfo.getPassword())) {
                // 密码不对
                throw new BadCredentialsException("密码错误！");
            }
        } else {
            throw new BadCredentialsException("不支持的登录类型");
        }

        Collection<? extends GrantedAuthority> authorities = userInfo.getAuthorities();
        return new UsernamePasswordAuthenticationToken(userInfo, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static Map<LoginType, LoginUserService> getType2service() {
        return type2service;
    }
}
