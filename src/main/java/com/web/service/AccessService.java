package com.web.service;

import com.web.constant.CookieConstant;
import com.web.repository.entity.UserTokenEntity;
import com.web.util.CookieUtil;
import com.web.util.DateUtil;
import com.web.util.ServletUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;

import java.lang.annotation.Annotation;
import java.sql.Timestamp;

/**
 * 基础功能服务
 *
 * @author shenjy
 * @time 2023/12/20 11:19
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AccessService {

    /**
     * 跨域配置
     **/
    @Value("${server.domain:}")
    private String serverDomain;

    /**
     * 设置Cookie
     *
     * @param token 登录token
     */
    public void setCookie(String token) {
        CookieUtil.setCookie(ServletUtil.getResponse(), CookieConstant.COOKIE_KEY_TOKEN, token,
                CookieConstant.TOKEN_EXPIRED_MS / 1000, serverDomain);
    }

    /**
     * 删除Cookie
     */
    public void removeCookie() {
        CookieUtil.removeCookie(ServletUtil.getResponse(), CookieConstant.COOKIE_KEY_TOKEN, serverDomain);
    }

    /**
     * 判断是否有指定注解
     *
     * @param method          处理器
     * @param annotationClass 注解类
     * @return 是否有指定注解
     */
    public boolean hasAnnotation(HandlerMethod method, Class<? extends Annotation> annotationClass) {
        return method.hasMethodAnnotation(annotationClass) ||
                method.getBeanType().isAnnotationPresent(annotationClass);
    }

    /**
     * 获取token的过期时间
     *
     * @return token的过期时间
     */
    public Timestamp getTokenExpireTime() {
        long timestamp = System.currentTimeMillis() + CookieConstant.TOKEN_EXPIRED_MS;
        return new Timestamp(timestamp);
    }

    /**
     * 校验token是否有效
     *
     * @param userToken token实体
     * @return 是否有效
     */
    public boolean isExpiredToken(UserTokenEntity userToken) {
        if (null == userToken || null == userToken.getExpireTime()) {
            return true;
        }
        return DateUtil.getCurrentTime().compareTo(userToken.getExpireTime()) > 0;
    }
}
