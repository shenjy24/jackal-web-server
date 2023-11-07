package com.web.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

public class CookieUtil {
    public static final String TOKEN = "token";

    /**
     * 封装设置cookie方法
     *
     * @param response
     * @param cookieValue
     * @param cookieName
     * @param maxAge
     */
    public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue, int maxAge) {
        setCookie(response, cookieName, cookieValue, maxAge, "");
    }

    public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue, int maxAge, String domain) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath("/");//总域名底下都能找到
        cookie.setMaxAge(maxAge);
        if (StringUtils.isNotBlank(domain)) {
            cookie.setDomain(domain);
        }
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    public static void removeCookie(HttpServletResponse response, String cookieName, String domain) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setPath("/");//总域名底下都能找到
        cookie.setMaxAge(0);
        if (StringUtils.isNotBlank(domain)) {
            cookie.setDomain(domain);
        }
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    public static String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return "";
        }
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(TOKEN)) {
                return cookie.getValue();
            }
        }
        return "";
    }
}
