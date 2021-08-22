package com.cnsmash.config.login;

import com.cnsmash.pojo.entity.ReposResult;
import com.cnsmash.util.JsonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author guanhuan_li
 * #date 2020/8/26 14:43
 */
public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setStatus(HttpStatus.OK.value());
        ReposResult<Void> reposResult = ReposResult.error(HttpStatus.FORBIDDEN, e.getMessage());
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().print(JsonUtil.toJson(reposResult));
    }
}
