package com.web.config.access;

import com.web.config.GlobalConfig;
import com.web.config.response.BizException;
import com.web.config.response.SystemCode;
import com.web.constant.CookieConstant;
import com.web.util.CookieUtil;
import com.web.util.DateUtil;
import com.web.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * 登录拦截器
 *
 * @author shenjy
 * @time 2023/11/7 14:37
 */
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final GlobalConfig configItem;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        // 有Anonymous注解的方法可以匿名访问
        if (hasAnnotation(handlerMethod, Anonymous.class)) {
            return true;
        }
        Map<String, Object> payload = TokenUtil.getTokenPayload();
        if (payload == null || payload.isEmpty()) {
            throw new BizException(SystemCode.NO_AUTH);
        }
        // 检测是否需要延迟过期时间
        long userId = (long) payload.get(CookieConstant.TOKEN_KEY_UID);
        int currentTime = DateUtil.currentSecond();
        long expireTime = (long) payload.get(CookieConstant.TOKEN_KEY_EXPIRED);
        if (expireTime - currentTime < CookieConstant.TOKEN_REFRESH_TIME) {
            expireTime = currentTime + CookieConstant.TOKEN_EXPIRED_TIME;
            String token = TokenUtil.generateToken(userId, expireTime);
            CookieUtil.setCookie(response, CookieConstant.COOKIE_KEY_TOKEN, token,
                    CookieConstant.TOKEN_EXPIRED_TIME, configItem.getServerDomain());
        }

        request.setAttribute(CookieConstant.REQ_ATT_USER, userId);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private boolean hasAnnotation(HandlerMethod method, Class<? extends Annotation> annotationClass) {
        return method.hasMethodAnnotation(annotationClass) ||
                method.getBeanType().isAnnotationPresent(annotationClass);
    }
}
