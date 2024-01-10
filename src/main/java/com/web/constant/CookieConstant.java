package com.web.constant;

public class CookieConstant {
    public static final String COOKIE_KEY_TOKEN = "token";
    public static final String TOKEN_KEY_UID = "uid";
    public static final String TOKEN_KEY_EXPIRED = "exp";
    public static final String REQ_ATT_USER = "attr-user";
    public static final int TOKEN_EXPIRED_MS = 15 * 24 * 60 * 60 * 1000; // token过期时间，单位毫秒
    public static final int TOKEN_REFRESH_MS = 60 * 60 * 1000;  // token刷新时间，单位毫秒
}
