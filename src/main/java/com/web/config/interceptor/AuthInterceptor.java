package com.web.config.interceptor;

import com.web.config.access.Anonymous;
import com.web.config.response.BizException;
import com.web.config.response.SystemCode;
import com.web.constant.CookieConstant;
import com.web.repository.entity.UserTokenEntity;
import com.web.service.AccessService;
import com.web.service.user.UserService;
import com.web.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录拦截器
 *
 * @author shenjy
 * @time 2023/11/7 14:37
 */
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final UserService userService;
    private final AccessService accessService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        // 有Anonymous注解的方法可以匿名访问
        if (accessService.hasAnnotation(handlerMethod, Anonymous.class)) {
            return true;
        }
        String token = CookieUtil.getToken(request);
        if (StringUtils.isBlank(token)) {
            throw new BizException(SystemCode.NO_AUTH);
        }
        UserTokenEntity userToken = userService.getUserTokenByToken(token);
        if (accessService.isExpiredToken(userToken)) {
            throw new BizException(SystemCode.NO_AUTH);
        }

        // 检测是否需要延迟过期时间，临近指定时长则延长过期时间
        long currentTime = System.currentTimeMillis();
        long expireTime = userToken.getExpireTime().getTime();
        if (expireTime - currentTime < CookieConstant.TOKEN_REFRESH_MS) {
            userToken.setExpireTime(accessService.getTokenExpireTime());
            userService.updateUserToken(userToken);
        }

        request.setAttribute(CookieConstant.REQ_ATT_USER, userToken.getUserId());
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
