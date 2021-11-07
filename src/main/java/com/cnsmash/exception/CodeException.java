package com.cnsmash.exception;

/**
 * 自定义编码异常
 *
 * @author lhb 2020/10/30 13:39
 **/
public class CodeException extends RuntimeException {

    private final int code;

    private Object[] args;

    public int getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

    public CodeException(int code) {
        this.code = code;
    }

    public CodeException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public CodeException(int code, Throwable throwable) {
        super(throwable);
        this.code = code;
    }
}
