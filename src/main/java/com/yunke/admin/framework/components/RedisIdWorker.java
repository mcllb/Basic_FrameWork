package com.yunke.admin.framework.components;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Component
public class RedisIdWorker {

    /**
     * 开始时间戳,此处的时间戳为预生成
     */
    private static final long BEGIN_TIMESTAMP = 1640995200L;
    /**
     * 序列号的位数
     */
    private static final int COUNT_BITS = 32;

    private static final String DEFAULT_KEY = "default";

    private StringRedisTemplate stringRedisTemplate;

    public RedisIdWorker(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 描述信息: 生成唯一id
     *
     * @date  2023/05/21
     * @param keyPrefix 前缀，用于区分不同的业务
     * @return long id
     **/
    public long nextId(String keyPrefix) {
        // 1.生成时间戳
        LocalDateTime now = LocalDateTime.now();
        long nowSecond = now.toEpochSecond(ZoneOffset.UTC);
        long timestamp = nowSecond - BEGIN_TIMESTAMP;

        // 2.生成序列号
        // 2.1.获取当前日期，精确到天
        String date = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        // 2.2.自增长 key格式：icr:order:2023:05:21
        long count = stringRedisTemplate.opsForValue().increment("icr:" + keyPrefix + ":" + date);

        // 3.拼接并返回，目的是让时间戳存放在32上，序列号在低32位
        return timestamp << COUNT_BITS | count;
    }

    public long nextId() {
        return nextId(DEFAULT_KEY);
    }


    public String nextIdStr() {
        return nextIdStr(DEFAULT_KEY);
    }

    public String nextIdStr(String keyPrefix){
        return nextId(keyPrefix) + "";
    }

}
