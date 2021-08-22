package com.cnsmash.config;

import com.cnsmash.pojo.entity.ReposResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author guanhuan_li
 * #date 2020/8/26 14:43
 */
@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(AccessDeniedException.class)
    public ReposResult<Void> handleValidationBodyException(AccessDeniedException e) {
        log.error("权限不足:"+e);
        return ReposResult.error(HttpStatus.UNAUTHORIZED, "权限不足！");
    }
}
