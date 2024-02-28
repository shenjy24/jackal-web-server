package com.web.config.interceptor;

import com.web.config.access.User;
import com.web.config.access.UserArg;
import com.web.constant.CookieConstant;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@Component
public class UserArgResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(UserArg.class)
                && parameter.hasParameterAnnotation(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Long userId = (Long) webRequest.getAttribute(CookieConstant.REQ_ATT_USER, RequestAttributes.SCOPE_REQUEST);
        if (userId == null) {
            throw new MissingServletRequestPartException(CookieConstant.REQ_ATT_USER);
        }
        UserArg userArg = new UserArg();
        userArg.setUserId(userId);
        return userArg;
    }
}
