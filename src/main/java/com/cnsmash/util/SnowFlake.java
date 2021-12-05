package com.cnsmash.util;

/**
 * @author guanhuan
 */
public abstract class SnowFlake {

    /**
     * 起始的时间戳
     */
    private final static long TWEPOCH = 1557825652094L;

    /**
     * 每一部分占用的位数
     */
    private final static long WORKER_ID_BITS = 5L;
    private final static long DATACENTER_ID_BITS = 5L;
    private final static long SEQUENCE_BITS = 12L;

    /**
     * 每一部分的最大值
     */
    private final static long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    private final static long MAX_DATACENTER_ID = ~(-1L << DATACENTER_ID_BITS);
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS);

    /**
     * 每一部分向左的位移
     */
    private final static long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private final static long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    private final static long TIMESTAMP_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;
    /** 数据中心ID */
    private static final long DATACENTER_ID;

    /** 机器ID */
    private static final long WORKER_ID;
    /** 序列号 */
    private static long sequence = 0L;
    /** 上一次时间戳 */
    private static long lastTimestamp = -1L;

    static{
        DATACENTER_ID = 1L;
        WORKER_ID = 1L;
    }

    public static synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format(
                    "Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0L) {
                timestamp = tilNextMillis();
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        // 时间戳部分 数据中心部分 机器标识部分 序列号部分
        return (timestamp - TWEPOCH) << TIMESTAMP_SHIFT
                | DATACENTER_ID << DATACENTER_ID_SHIFT
                | WORKER_ID << WORKER_ID_SHIFT
                | sequence;
    }

    private static long tilNextMillis() {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private static long timeGen() {
        return System.currentTimeMillis();
    }
}