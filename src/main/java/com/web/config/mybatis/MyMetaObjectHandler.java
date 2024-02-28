package com.web.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * @author shenjy
 * @createTime 2022/2/24 10:14
 * @description MybatisPlusConfig
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.strictInsertFill(metaObject, "createTime", () -> new Timestamp(System.currentTimeMillis()), Timestamp.class);
        this.strictInsertFill(metaObject, "updateTime", () -> new Timestamp(System.currentTimeMillis()), Timestamp.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "updateTime", () -> new Timestamp(System.currentTimeMillis()), Timestamp.class);
    }
}
