package com.web.util;

import java.sql.Timestamp;

/**
 * 【 时间工具类 】
 *
 * @author shenjy 2018/08/25
 */
public class DateUtil {

    public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String FORMAT_ZERO_ZONE = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public static final Integer MINUTE_SECOND = 60;

    public static final Integer HOUR_SECOND = 60 * 60;

    public static final Integer DAY_SECOND = HOUR_SECOND * 24;

    public static final Integer MONTH_SECOND = DAY_SECOND * 30;

    public static final Integer YEAR_SECOND = MONTH_SECOND * 12;

    /**
     * 获取当前时间
     *
     * @return 当前时间
     */
    public static Timestamp getCurrentTime() {
        return new Timestamp(System.currentTimeMillis());
    }
}
