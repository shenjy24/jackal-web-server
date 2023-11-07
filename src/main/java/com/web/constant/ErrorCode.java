package com.web.constant;

/**
 * 业务错误码
 *
 * @author shenjy
 * @time 2020/8/13
 */
public enum ErrorCode {
    /**
     * 通用异常
     */
    UPDATE_ERROR(100001,"更新异常"),

    /**
     * 用户模块
     */
    USER_ERROR1(200001, "用户已存在"),
    USER_ERROR2(200002, "账号或密码错误"),
    USER_ERROR3(200003, "用户不存在"),
    ;

    private final Integer code;
    private final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
