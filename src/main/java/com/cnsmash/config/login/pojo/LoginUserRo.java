package com.cnsmash.config.login.pojo;

import com.cnsmash.config.login.MateAuthenticationDetailsSource;
import lombok.Data;

/**
 * @author guanhuan_li
 * #date  2020/12/2 15:58
 */
@Data
public class LoginUserRo {

    public LoginUserRo(String username, MateAuthenticationDetailsSource.MateWebAuthenticationDetails details) {
        this.username = username;
        this.details = details;
    }

    private String username;
    private MateAuthenticationDetailsSource.MateWebAuthenticationDetails details;

}
