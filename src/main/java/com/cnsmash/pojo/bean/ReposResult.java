package com.cnsmash.pojo.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Json 统一返回消息类
 *
 * @author lgh
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ReposResult<T> implements Serializable {

    /**
     * 上下文
     */
    private String context;

    /**
     * 处理状态：200: 成功 500:失败
     */
    private int status;

    private T message;

    private String error;

    public long getTimestamp() {
        return timestamp;
    }

    private long timestamp = System.currentTimeMillis();

    public ReposResult() {
    }

    public ReposResult(T message) {
        setStatus(HttpStatus.OK.value());
        this.message = message;
    }

    public ReposResult(int code, String error) {
        setStatus(code);
        this.error = error;
    }

    public T get() {
        if (message == null) {
            throw new NoSuchElementException("No message present");
        }
        return message;
    }

    /**
     * 处理成功，并返回数据
     *
     * @param message 数据对象
     * @return data
     */
    public static <T> ReposResult<T> ok(T message) {
        return new ReposResult(message);
    }

    /**
     * 处理成功，并返回数据
     *
     * @return ReposResult 数据对象
     */
    public static <T> ReposResult<T> ok() {
        return new ReposResult(null);
    }


    /**
     * 处理成功，并返回数据
     *
     * @param message 数据对象
     * @return data
     */
    @Deprecated
    public ReposResult<T> success(T message) {
        this.setStatus(HttpStatus.OK.value());
        this.setMessage(message);
        return this;
    }

    /**
     * 处理成功
     * <p>
     * 消息
     *
     * @return data
     */
    @Deprecated
    public ReposResult<T> success() {
        this.setStatus(HttpStatus.OK.value());
        return this;
    }


    /**
     * 处理失败，并返回数据（一般为错误信息）
     *
     * @param status 错误代码
     * @param error  消息
     * @return data
     */
    @Deprecated
    public ReposResult<T> failure(HttpStatus status, String error) {
        this.setStatus(status.value());
        this.setError(error);
        return this;
    }


    /**
     * 处理失败
     *
     * @param error 消息
     * @return data
     */
    @Deprecated
    public ReposResult<T> failure(String error) {
        return failure(HttpStatus.INTERNAL_SERVER_ERROR, error);
    }

    /**
     * 处理失败
     *
     * @param throwable 消息
     * @return data
     */
    @Deprecated
    public ReposResult<T> failure(Throwable throwable) {
        return failure(HttpStatus.INTERNAL_SERVER_ERROR, throwable.getMessage());
    }

    /**
     * 处理失败
     *
     * @param status 错误代码
     * @param error  消息
     * @return data
     */
    public static <T> ReposResult<T> error(HttpStatus status, String error) {
        return error(status.value(), error);
    }

    /**
     * 处理失败
     *
     * @param status 错误代码
     * @param error  消息
     * @return data
     */
    public static <T> ReposResult<T> error(int status, String error) {
        return new ReposResult(status, error);
    }

    /**
     * 处理失败
     *
     * @param error 消息
     * @return data
     */
    public static <T> ReposResult<T> error(String error) {
        return new ReposResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), error);
    }

    /**
     * 处理失败
     *
     * @param throwable 消息
     * @return data
     */
    public static <T> ReposResult<T> error(Throwable throwable) {
        return new ReposResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), throwable.getMessage());
    }


    public ReposResult<T> ok(Consumer<T> consumer) {
        if (this.isSuccess()) {
            consumer.accept(this.message);
        }
        return this;
    }

    /**
     * 200状态码转换数据类型
     *
     * @param function
     * @param <R>      返回数据类型，默认
     * @return
     */
    public <R> R mapOk(Function<T, R> function) {
        if (this.isSuccess()) {
            return function.apply(this.message);
        }
        return null;
    }


    public <X extends Throwable> ReposResult<T> errorThrow(Function<String, ? extends X> function) throws X {
        if (!this.isSuccess()) {
            throw function.apply(this.error);
        }

        return this;
    }

    public ReposResult<T> error(Consumer<String> consumer) {
        if (!this.isSuccess()) {
            consumer.accept(this.error);
        }
        return this;
    }

    /**
     * 非200状态码转换数据类型
     *
     * @param function
     * @param <R>      返回数据类型，默认
     * @return
     */
    public <R> R mapError(BiFunction<T, String, R> function) {
        if (!this.isSuccess()) {
            return function.apply(this.getMessage(), this.getError());
        }
        return null;
    }

    /**
     * 当某种状态码
     *
     * @param httpStatus
     * @param consumer
     * @return
     */
    public ReposResult<T> statusError(HttpStatus httpStatus, Consumer<String> consumer) {
        return statusError(httpStatus.value(), consumer);
    }

    /**
     * 当某种状态码
     *
     * @param status
     * @param consumer
     * @return
     */
    public ReposResult<T> statusError(int status, Consumer<String> consumer) {
        if (this.getStatus() == status) {
            consumer.accept(this.error);
        }
        return this;
    }

    public ReposResult<T> statusError(BiConsumer<Integer, String> consumer) {
        if (!this.isSuccess()) {
            consumer.accept(this.status, this.error);
        }
        return this;
    }


    public <X extends Throwable> ReposResult<T> statusThrow(BiFunction<Integer, String, ? extends X> function) throws X {
        if (!this.isSuccess()) {
            throw function.apply(this.status, this.error);
        }
        return this;
    }

    /**
     * 当某种状态码,返回自定义对象
     *
     * @param httpStatus 状态码
     * @param function   函数参数 message, error
     * @return
     */
    public <R> R mapStatus(HttpStatus httpStatus, BiFunction<T, String, R> function) {
        if (this.getStatus() == httpStatus.value()) {
            return function.apply(this.getMessage(), this.error);
        }
        return null;
    }

    /**
     * 当某种状态码
     *
     * @param status
     * @param function
     * @return
     */
    public <R> R mapStatus(int status, BiFunction<T, String, R> function) {
        if (this.getStatus() == status) {
            return function.apply(this.getMessage(), this.error);
        }
        return null;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.getStatus() == HttpStatus.OK.value();
    }

    @Override
    public String toString() {
        return "ReposResult [status=" + status + ", message=" + message + ",error=" + error + ",context=" + context + "]";
    }
}
