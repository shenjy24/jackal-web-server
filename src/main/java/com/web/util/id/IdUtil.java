package com.web.util.id;

import java.net.InetAddress;

/**
 * 雪花算法ID生成工具
 *
 * @author shenjy
 * @time 2022/2/24 11:25
 */
public class IdUtil {

    private final Sequence sequence;

    public IdUtil() {
        this.sequence = new Sequence(null);
    }

    public IdUtil(InetAddress inetAddress) {
        this.sequence = new Sequence(inetAddress);
    }

    public IdUtil(long workerId, long dataCenterId) {
        this.sequence = new Sequence(workerId, dataCenterId);
    }

    public IdUtil(Sequence sequence) {
        this.sequence = sequence;
    }

    public Long nextId() {
        return sequence.nextId();
    }
}
