package com.springboot.utils.myHttpResult;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@Data
public class HttpResult<T> implements Serializable {
    /**
     * 返回值code：
     * 200 表示处理成功
     * 400 表示参数错误
     * 404 请求的资源（网页等）不存在
     * 500 表示服务器内部错误
     * 503 表示当前服务不可用
     * 504 表示网关超时
     */
    private Integer code;

    /**
     * 返回值信息，包含成功信息和失败信息
     */
    private String msg;

    /**
     * 返回成功标示，通过这个值，可以直接观察到该次请求是否成功。
     */
    private Boolean success;

    /**
     * 返回对象，用于请求成功后，返回客户端需要的数据。
     */
    private T data;

    /**
     * 无参构造器
     */
    public HttpResult() {
        this.code = 200;
        this.success = true;
    }

    /**
     * 返回成功的实体
     *
     * @param data
     */
    public HttpResult(T data) {
        this.code = 200;
        this.data = data;
        this.success = true;
    }

    /**
     * 返回成功的实体
     *
     * @param msg
     * @param data
     */
    public HttpResult(String msg, T data) {
        this.code = 200;
        this.msg = msg;
        this.data = data;
        this.success = true;
    }

    /**
     * 返回错误信息
     *
     * @param resultCode
     */
    public HttpResult(ResultCode resultCode) {
        this.success = true;
        this.code = resultCode.getCode();
        this.msg = resultCode.getMessage();
        this.data = null;
    }

    /**
     * 返回自定义信息
     *
     * @param success 是否成功
     * @param code    代码
     * @param message 消息
     * @param data    数据
     */
    public HttpResult(boolean success, int code, String message, T data) {
        this.success = success;
        this.code = code;
        this.msg = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }
}
