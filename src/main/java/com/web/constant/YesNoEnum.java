package com.web.constant;

/**
 * YesNoEnum
 *
 * @author shenjy
 * @time 2023/10/23 10:10
 */
public enum YesNoEnum {
    NO(0, "NO"),
    YES(1, "YES"),
    ;

    private final Integer code;

    private final String message;

    YesNoEnum(Integer code, String message) {
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
