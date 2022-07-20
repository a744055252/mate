package com.cnsmash.config;

import com.cnsmash.config.login.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.server.session.DefaultWebSessionManager;
import org.springframework.web.server.session.HeaderWebSessionIdResolver;
import org.springframework.web.server.session.WebSessionIdResolver;

/**
 * @author guanhuan_li
 * #date 2020/8/26 14:43
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MateAuthenticationProvider mateAuthenticationProvider;

    @Autowired
    MateAuthenticationDetailsSource mateAuthenticationDetailsSource;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(mateAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors();

        http.csrf().disable();
        http
            .formLogin()
            .authenticationDetailsSource(mateAuthenticationDetailsSource)
            .successHandler(new DefaultSuccessHandler())
            .failureHandler(new DefaultFailureHandler())
            .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessHandler(new DefaultLogoutSuccessHandler())
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(new DefaultAuthenticationEntryPoint())
            .and()
            .authorizeRequests()
            .antMatchers("/actuator/**",
                    "/account/register",
                    "/battle/detail", "/battle/page", "/battle/onlineCount",
                    "/comment/**",
                    "/file/**",
                    "/freeroom/list", "/freeroom/detail", "/freeroom/count",
                    "/quarter/**",
                    "/rank/id", "/rank/total", "/rank/fullrank",
                    "/user/id", "/user/fighter", "/user/recountFighter",
                    "/tournament/list", "/tournament/user", "/tournament/playerList",
                    "/tournament/setlist", "/tournament/resultList", "/tournament/detail",
                    "/account/user","account/wechat"
                    )
                .permitAll()
            .anyRequest()
                .authenticated();

        // 将已登录者挤下线
        http.sessionManagement().maximumSessions(1);
    }

}
