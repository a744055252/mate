package com.cnsmash.util;

import com.cnsmash.config.login.pojo.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author guanhuan_li
 * #date  2020/11/24 18:47
 */
public abstract class MateAuthUtils {

    public static LoginUser getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (LoginUser) authentication.getPrincipal();
    }


}
