package com.web.service;

import com.web.config.GlobalConfig;
import com.web.util.id.IdUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * ID服务类
 *
 * @author shenjy
 * @time 2023/9/11 11:45
 */
@Component
@RequiredArgsConstructor
public class IdService {

    private IdUtil idUtil;
    private final GlobalConfig configItem;

    @PostConstruct
    public void init() {
        idUtil = new IdUtil(configItem.getWorkerId(), configItem.getDataCenterId());
    }

    /**
     * 去掉横杆的UUID值
     *
     * @return UUID
     */
    public String uuid() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }

    /**
     * 获取雪花算法ID
     *
     * @return 雪花算法ID
     */
    public long objectId() {
        return idUtil.nextId();
    }
}
