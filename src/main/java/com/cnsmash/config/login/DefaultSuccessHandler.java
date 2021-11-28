package com.cnsmash.config.login;

import com.cnsmash.config.login.pojo.LoginType;
import com.cnsmash.config.login.pojo.LoginUser;
import com.cnsmash.config.login.pojo.LoginUserVo;
import com.cnsmash.config.login.service.LoginUserService;
import com.cnsmash.pojo.bean.ReposResult;
import com.cnsmash.util.JsonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author guanhuan_li
 * #date 2020/8/26 14:43
 */
public class DefaultSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
        // 登录用户
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 获取完整数据
        Map<LoginType, LoginUserService> type2service = MateAuthenticationProvider.getType2service();
        LoginUserVo loginUserVo = type2service.get(loginUser.getLoginType()).getLoginUserVo(loginUser);
        String sessionId = httpServletRequest.getSession().getId();
        loginUserVo.setToken(sessionId);

        httpServletResponse.setStatus(HttpStatus.OK.value());
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().print(JsonUtil.toJson(ReposResult.ok(loginUserVo)));
    }


}
