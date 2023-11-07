package com.web.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ServletUtil {
    public static ServletRequestAttributes getRequestAttributes() {
        try {
            RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
            return (ServletRequestAttributes) attributes;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前请求实例
     *
     * @return 当前请求实例
     */
    public static HttpServletRequest getRequest() {
        try {
            ServletRequestAttributes att;
            return ((att = getRequestAttributes()) != null) ? att.getRequest() : null;
        } catch (Exception e) {
            return null;
        }
    }

    public static HttpServletResponse getResponse() {
        try {
            ServletRequestAttributes att;
            return ((att = getRequestAttributes()) != null) ? att.getResponse() : null;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getIp() {
        HttpServletRequest req = getRequest();
        return (req != null) ? req.getRemoteAddr() : null;
    }
}
