package com.web.util;

import java.util.UUID;

/**
 * IdUtil
 *
 * @author shenjy
 * @time 2023/12/21 09:31
 */
public class IdUtil {

    public static String uuid() {
        UUID uuid = UUID.randomUUID();
        // 去除UUID中的连字符
        return uuid.toString().replace("-", "");
    }
}
