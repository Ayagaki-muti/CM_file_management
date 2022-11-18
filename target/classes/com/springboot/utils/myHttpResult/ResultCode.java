package com.springboot.utils.myHttpResult;

public enum ResultCode {
    /*** 通用部分 100 - 599***/

    // 成功请求
    SUCCESS(200, "successful"),
    // 重定向
    REDIRECT(301, "redirect"),
    // 参数错误
    PARAMETER_ERROR(400, "parameter error"),
    // 资源未找到
    NOT_FOUND(404, "not found"),
    // 服务器错误
    SERVER_ERROR(500, "server error"),
    // 当前服务不可用
    SERVICE_UNAVAILABLE(503, "the current service unavailable "),
    // 网关超时
    GATEWAY_TIMEOUT(504, "Gateway timeout ");

    private int code;
    private String message;

    ResultCode(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public static ResultCode valueOf(int code) {
        for (ResultCode value : values()) {
            if (code == value.code) {
                return value;
            }
        }
        return SUCCESS;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean success() {
        return this.code == 200;
    }
}
