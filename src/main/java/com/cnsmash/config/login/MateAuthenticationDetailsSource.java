package com.cnsmash.config.login;

import com.cnsmash.config.login.pojo.LoginType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author guanhuan_li
 * #date 2020/8/26 14:43
 */
@Component
public class MateAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {

    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new MateWebAuthenticationDetails(context);
    }


    public static class MateWebAuthenticationDetails extends WebAuthenticationDetails {

        private static final long serialVersionUID = -458868165335802839L;

        private final LoginType loginType;

        private final HttpSession httpSession;

        private final static String TYPE_PARAM = "loginType";

        private MateWebAuthenticationDetails(HttpServletRequest request) {
            super(request);
            loginType = LoginType.valueOf(request.getParameter(TYPE_PARAM));
            httpSession = request.getSession();
        }

        public LoginType getLoginType(){
            return this.loginType;
        }

        public HttpSession getHttpSession() {
            return httpSession;
        }
    }

}
