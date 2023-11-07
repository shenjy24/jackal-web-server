package com.web.util;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.web.constant.CookieConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT(JSON WEB TOKEN) 工具
 *
 * @author shenjy 2022/07/13
 */
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    private static final byte[] secret = "vQ0/nE6{pS0?rL4;lF4`uY7)vY1`vI5*jF5/".getBytes();

    /**
     * HS256 对称加密
     *
     * @param payloadMap 附加数据
     * @return token
     */
    public static String generateToken(Map<String, Object> payloadMap) {
        try {
            JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
            Payload payload = new Payload(payloadMap);
            JWSObject jwsObject = new JWSObject(jwsHeader, payload);
            JWSSigner jwsSigner = new MACSigner(secret);
            jwsObject.sign(jwsSigner);

            return jwsObject.serialize();
        } catch (JOSEException e) {
            logger.error("生成token异常：", e);
            return null;
        }
    }

    /**
     * 验证token
     *
     * @param token JWT
     * @return token验证是否通过
     * @throws ParseException
     * @throws JOSEException
     */
    public static boolean checkToken(String token) {
        if (StringUtils.isBlank(token)) {
            return false;
        }
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            JWSVerifier jwsVerifier = new MACVerifier(secret);
            if (!jwsObject.verify(jwsVerifier)) {
                logger.warn("token校验不通过，token：{}", token);
                return false;
            }
            Map<String, Object> jsonObject = jwsObject.getPayload().toJSONObject();
            //判断token是否过期
            if (jsonObject.containsKey(CookieConstant.TOKEN_KEY_EXPIRED)) {
                int expTime = Integer.parseInt(jsonObject.get(CookieConstant.TOKEN_KEY_EXPIRED).toString());
                int nowTime = (int) (System.currentTimeMillis() / 1000L);
                if (nowTime > expTime) {
                    logger.warn("token已过期，token：{}", token);
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            logger.error("校验token异常，token：" + token, e);
            return false;
        }
    }

    /**
     * 获取token中包含的数据
     *
     * @param token JWT
     * @return token中包含的数据
     * @throws ParseException
     * @throws JOSEException
     */
    public static Map<String, Object> getPayload(String token) {
        try {
            if (checkToken(token)) {
                JWSObject jwsObject = JWSObject.parse(token);
                return jwsObject.getPayload().toJSONObject();
            }
        } catch (ParseException e) {
            logger.error("获取payload异常：", e);
        }
        return new HashMap<>();
    }

    public static String getAccount(String token) {
        if (StringUtils.isBlank(token)) {
            return "";
        }
        Map<String, Object> payload = getPayload(token);
        return (String) payload.getOrDefault(CookieConstant.TOKEN_KEY_UID, "");
    }
}
