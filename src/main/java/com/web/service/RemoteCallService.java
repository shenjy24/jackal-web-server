package com.web.service;

import com.web.config.response.BizException;
import com.web.config.response.JsonResult;
import com.web.config.response.SystemCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 调用远程接口的服务
 *
 * @author shenjy
 * @time 2023/7/27 23:13
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RemoteCallService {
    private final RestTemplate restTemplate;

    /**
     * 调用远程接口方法
     *
     * @param url         接口路径
     * @param requestData 请求体，JSON格式
     * @return 响应体
     */
    public JsonResult callRemoteApi(String url, String requestData) {
        try {
            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            // 封装请求体
            HttpEntity<String> request = new HttpEntity<>(requestData, headers);
            return restTemplate.postForObject(url, request, JsonResult.class);
        } catch (Exception e) {
            log.error(String.format("调用远程接口异常,api=%s,req=%s", url, requestData), e);
            throw new BizException(SystemCode.SERVER_ERROR);
        }
    }

    /**
     * 调用远程接口方法
     *
     * @param url         接口路径
     * @param requestData 请求体，Form格式
     * @return 响应体
     */
    public JsonResult callRemoteApi(String url, Map<String, Object> requestData) {
        try {
            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            // 封装请求体
            MultiValueMap<String, Object> multiValueMap = toMultiValueMap(requestData);
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(multiValueMap, headers);

            return restTemplate.postForObject(url, requestEntity, JsonResult.class);
        } catch (Exception e) {
            log.error(String.format("调用远程接口异常,api=%s,req=%s", url, requestData), e);
            throw new BizException(SystemCode.SERVER_ERROR);
        }
    }

    private MultiValueMap<String, Object> toMultiValueMap(Map<String, Object> map) {
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            multiValueMap.add(entry.getKey(), entry.getValue());
        }
        return multiValueMap;
    }
}
