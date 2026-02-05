package com.yunke.admin.common.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.yunke.admin.framework.config.ProjectConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Component
public class RedisIDUtil {

    private String commonKeyPrefix;

    public final static int DEFAULT_SEQ_LENGTH = 6;

    public final static int SEQ_LENGTH_MIN = 4;

    public final static int SEQ_LENGTH_MAX = 10;


    @Autowired
    private RedisTemplate redisTemplate;

    public RedisIDUtil() {
        ProjectConfig projectConfig = SpringUtil.getBean(ProjectConfig.class);
        if(StrUtil.isNotEmpty(projectConfig.getRedisKeyPrefix())){
            commonKeyPrefix = projectConfig.getRedisKeyPrefix()+":GEN_ID:";
        }else{
            commonKeyPrefix = SaUtil.getTokenName() + "-APP:GEN_ID:";
        }
    }

    /**
     * 生成全局唯一顺序号
     * 规则为八位当天年月日 + 指定长度流失号
     * 每天0点重新生成
     *
     * @param key 缓存key
     * @param seqLength  流水号长度，最小4位，最大10位
     * @return
     */
    public Long nextId(String key, int seqLength){
        Assert.isTrue(seqLength >= SEQ_LENGTH_MIN && seqLength <= SEQ_LENGTH_MAX," seqLength must be in between 4 and 10 ");
        String cacheKey = commonKeyPrefix + key;
        RedisAtomicLong counter = new RedisAtomicLong(cacheKey, redisTemplate.getConnectionFactory());
        if(counter.get() == 0L){
            if(seqLength < SEQ_LENGTH_MIN){
                seqLength = SEQ_LENGTH_MIN;
            }else if(seqLength > SEQ_LENGTH_MAX){
                seqLength = SEQ_LENGTH_MAX;
            }
            String initSeq = StrUtil.fill("",'0',seqLength,true);
            String initVal = DateUtil.format(DateUtil.date(), DatePattern.PURE_DATE_PATTERN) + initSeq;
            counter.set(Long.parseLong(initVal));
        }
        long id = counter.incrementAndGet();
        String seqNoStr = String.valueOf(id).substring(8);
        long seqNo = Long.parseLong(seqNoStr);
        if(seqNo == 1L ){
            //每天零点失效重新生成
            long between = DateUtil.between(DateUtil.date(), DateUtil.beginOfDay(DateUtil.tomorrow()), DateUnit.SECOND);
            counter.expire(between, TimeUnit.SECONDS);
        }
        return id;
    }

    public Long nextId(String key){
        return this.nextId(key, DEFAULT_SEQ_LENGTH);
    }

    public String nextIdStr(String key, int seqLength){
        return String.valueOf(this.nextId(key,seqLength));
    }

    public String nextIdStr(String key){
        return String.valueOf(this.nextId(key));
    }

    /**
     * 开始时间戳，参考方法getTimesMap()
     */
    private static final long BEGIN_TIMESTAMP = 1645568542L;
    /**
     * 序列号的位数
     */
    private static final int COUNT_BITS = 32;

    private static final String DEFAULT_SEQID_KEY = "DEFAULT";

    public Long nextSequenceId(String key) {
        // 1.生成时间戳
        LocalDateTime now = LocalDateTime.now();
        long nowSecond = now.toEpochSecond(ZoneOffset.UTC);
        long timestamp = nowSecond - BEGIN_TIMESTAMP;

        // 2.生成序列号
        // 2.1.获取当前日期，精确到天
        String date = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        // 2.2.自增长
        long count = redisTemplate.opsForValue().increment("GEN_SEQID:" + DEFAULT_SEQID_KEY + ":" + date);

        // 3.拼接
        long id = timestamp << COUNT_BITS | count;
        return id;
    }

    public String nextSequenceIdStr(String key) {
        return nextSequenceId(key) + "";
    }

    public Long nextSequenceId() {
        return nextSequenceId(DEFAULT_SEQID_KEY);
    }

    public String nextSequenceIdStr() {
        return nextSequenceIdStr(DEFAULT_SEQID_KEY);
    }


    public static void main(String[] args) {

    }


}
