package com.web.config.response;

/**
 * 系统状态码
 *
 * @author shenjy
 * @time 2020/8/13
 */
public enum  SystemCode implements CodeStatus {
    SUCCESS("2000", "success"),
    NO_AUTH("2001", "no auth"),
    SERVER_ERROR("2002", "server error"),
    ;

    private final String code;
    private final String message;

    SystemCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
