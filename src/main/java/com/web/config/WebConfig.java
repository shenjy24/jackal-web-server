package com.web.config;

import com.web.config.interceptor.AuthInterceptor;
import com.web.config.access.UserArgResolver;
import com.web.config.access.UserIdArgResolver;
import com.web.config.interceptor.CrossOriginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * WEB 配置
 *
 * @author shenjy
 * @time 2020/9/1
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final CrossOriginInterceptor crossOriginInterceptor;
    private final AuthInterceptor authInterceptor;
    private final UserArgResolver userArgResolver;
    private final UserIdArgResolver userIdArgResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(crossOriginInterceptor);
        registry.addInterceptor(authInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userArgResolver);
        resolvers.add(userIdArgResolver);
    }
}
