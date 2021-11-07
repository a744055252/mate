package com.cnsmash.exception;

/**
 * 提示类异常
 * @author guanhuan
 */
public class TipException extends RuntimeException {
    public TipException(){

    }

    public TipException(String message) {
        super(message);
    }

    /**
     * Constructs a new AuthorizationException.
     *
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public TipException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new AuthorizationException.
     *
     * @param message the reason for the exception
     * @param cause   the underlying Throwable that caused this exception to be thrown.
     */
    public TipException(String message, Throwable cause) {
        super(message, cause);
    }
}
