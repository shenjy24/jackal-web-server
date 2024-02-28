package com.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * RestTemplateConfig
 *
 * @author shenjy
 * @time 2024/1/10 16:24
 */
@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate() {
        // 创建 RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // 获取 RestTemplate 中的 HttpMessageConverter 列表
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();

        // 设置字符集编码为 UTF-8
        for (HttpMessageConverter<?> converter : messageConverters) {
            if (converter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) converter).setDefaultCharset(StandardCharsets.UTF_8);
            }
        }
        return restTemplate;
    }
}
