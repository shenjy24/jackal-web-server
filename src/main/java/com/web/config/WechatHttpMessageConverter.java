package com.web.config;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * 调用code2session接口时，返回的是JSON格式，但 content-type 却是 text/plain
 *
 * @author shenjy
 * @time 2023/12/20 20:56
 */
public class WechatHttpMessageConverter extends MappingJackson2HttpMessageConverter {
    public WechatHttpMessageConverter(){
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.TEXT_PLAIN);
        setSupportedMediaTypes(mediaTypes);
    }
}
