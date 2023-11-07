package com.web.util;

import com.web.constant.CookieConstant;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录token工具类
 *
 * @author shenjy
 * @time 2023/11/7 11:11
 */
@Service
public class TokenUtil {

    public static Map<String, Object> getTokenPayload() {
        HttpServletRequest req = ServletUtil.getRequest();
        if (req == null) {
            return null;
        }
        String token = CookieUtil.getToken(req);
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        return JwtUtil.getPayload(token);
    }

    public static String generateToken(long userId, long expired) {
        Map<String, Object> payload = new HashMap<>() {{
            put(CookieConstant.TOKEN_KEY_UID, userId);
            put(CookieConstant.TOKEN_KEY_EXPIRED, expired);
        }};
        return JwtUtil.generateToken(payload);
    }

}
