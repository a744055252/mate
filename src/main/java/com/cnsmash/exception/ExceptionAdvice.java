package com.cnsmash.exception;

import com.cnsmash.pojo.bean.ReposResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import java.rmi.RemoteException;

/**
 * @author guanhuan_li
 * #date 2020/8/26 14:43
 */
@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(AccessDeniedException.class)
    public ReposResult<String> handleValidationBodyException(AccessDeniedException e) {
        log.error("权限不足:"+e);
        return ReposResult.error(HttpStatus.UNAUTHORIZED, "权限不足！");
    }

    @ExceptionHandler(BindException.class)
    public ReposResult<String> handleBindException(BindException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        if (fieldError == null) {
            return ReposResult.error(303, "参数错误");
        }
        return ReposResult.error(303, "参数错误：【" + fieldError.getField() + "】" + fieldError.getDefaultMessage());
    }

    @ExceptionHandler({TypeMismatchException.class})
    @ResponseBody
    public ReposResult<String> requestTypeMismatch(TypeMismatchException ex, HttpServletRequest request) {
        log.debug("参数类型不匹配:[{}],[{}],[{}]", request.getRequestURI(), ex.getPropertyName(), ex.getRequiredType());
        return ReposResult.error(303, "参数类型不匹配:["+request.getRequestURI()+"],["+ex.getPropertyName()+"],["+ex.getRequiredType()+"]");
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public ReposResult<String> missingServletRequestParameter(MissingServletRequestParameterException ex, HttpServletRequest request) {
        log.debug("缺少必要参数:[{}],[{}]", request.getRequestURI(), ex.getParameterName());
        return ReposResult.error(303, "缺少必要参数:["+request.getRequestURI()+"],["+ex.getParameterName()+"]");
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    @ResponseBody
    public ReposResult<String> mediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
        log.debug("不支持的ContentType:[{}],[{}]", request.getRequestURI(), ex.getContentType());
        return ReposResult.error(303, "不支持的ContentType:["+request.getRequestURI()+"],["+ex.getContentType()+"]");
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    public ReposResult<String> methodNotSupported(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        log.debug("不支持的method:[{}],[{}]", request.getRequestURI(), ex.getMethod());
        return ReposResult.error(303, "不支持的不支持的method:["+request.getRequestURI()+"],["+ex.getMethod()+"]");
    }

    /**
     * 404
     *
     * @param ex 异常
     * @return 找不到页面
     */
    @ResponseBody
    @ExceptionHandler(NoHandlerFoundException.class)
    public ReposResult<String> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        log.debug("无效的访问地址:[{}]", ex.getRequestURL());
        return ReposResult.error(HttpStatus.NOT_FOUND, "无效的访问地址:[" + ex.getRequestURL() + "]");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ReposResult<String> handleBindException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        if (fieldError == null) {
            return ReposResult.error(HttpStatus.NOT_FOUND, "参数错误");
        }
        log.debug("参数错误:[{}],[{}]", fieldError.getField(), fieldError.getDefaultMessage());
        return ReposResult.error(HttpStatus.NOT_FOUND, "参数错误:["+fieldError.getField()+"],["+fieldError.getDefaultMessage()+"]");
    }

    /**
     * 303通用
     *
     * @param e 异常
     * @return 错误提示
     */
    @ResponseBody
    @ExceptionHandler(TipException.class)
    public ReposResult<String> handleTipException(TipException e, HttpServletRequest request) {
        log.debug("提示错误[{}]\n{}", request.getRequestURI(), ExceptionUtils.getStackTrace(e));
        return ReposResult.error(303, e.getMessage());
    }


    /**
     * 远程接口调用出错
     *
     * @param e 异常
     * @return 错误提示
     */
    @ResponseBody
    @ExceptionHandler(RemoteException.class)
    public ReposResult<String> handleRemoteException(RemoteException e) {
        log.error("远程接口调用出错!", e);
        return ReposResult.error(303, e.getMessage());
    }

    /**
     * 自定义编码异常
     *
     * @param e 异常
     * @return 错误提示
     */
    @ResponseBody
    @ExceptionHandler(CodeException.class)
    public ReposResult<String> handleCodeException(CodeException e, HttpServletRequest request) {
        log.debug("自定义错误[{}][{},{}]\n{}", request.getRequestURI(), e.getCode(), e.getMessage(), ExceptionUtils.getStackTrace(e));
        return ReposResult.error(e.getCode(), e.getMessage());
    }

    /**
     * 参数错误
     *
     * @param e       异常
     * @param request 请求
     * @return 错误提示
     */
    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public ReposResult<String> handleArgumentException(RuntimeException e, HttpServletRequest request) {
        log.debug("参数错误[{}]\n{}", request.getRequestURI(), ExceptionUtils.getStackTrace(e));
        return ReposResult.error(303, "参数错误");
    }

    /**
     * 提示没有权限
     *
     * @param e       异常
     * @param request 请求
     * @return 没有权限
     * @author yzh 2019-06-21 15:39
     */
    @ResponseBody
    @ExceptionHandler({AuthException.class})
    public ReposResult<String> handleUnauthorizedException(AuthException e, HttpServletRequest request) {
        log.debug("无权访问[{}]\n{}", request.getRequestURI(), ExceptionUtils.getStackTrace(e));
        return ReposResult.error(HttpStatus.FORBIDDEN, e.getMessage());
    }

    /**
     * 500通用
     *
     * @param e       异常
     * @param request 请求
     * @return 系统错误
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ReposResult<String> handleException(Exception e, HttpServletRequest request) {
        log.error("系统错误:[{}]", request.getRequestURI(), e);
        return ReposResult.error(HttpStatus.INTERNAL_SERVER_ERROR, "系统错误");
    }

}
