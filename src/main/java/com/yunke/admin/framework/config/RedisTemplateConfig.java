package com.yunke.admin.framework.config;

import com.yunke.admin.common.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
@AutoConfigureBefore(RedisAutoConfiguration.class)
@AutoConfigureAfter(ProjectConfig.class)
public class RedisTemplateConfig {

    @Autowired
    private ProjectConfig projectConfig;

    @Bean
    public RedisKeySerializer redisKeySerializer(){
        String keyPrefix = projectConfig.getRedisKeyPrefix() + CommonConstant.REDIS_KEY_SEPARATOR;
        return new RedisKeySerializer(keyPrefix);
    }


    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // key 序列化
        // key采用 String的序列化方式
        redisTemplate.setKeySerializer(redisKeySerializer());
        // hash的 key也采用 String的序列化方式
        redisTemplate.setHashKeySerializer(redisKeySerializer());

        // value 序列化
        redisTemplate.setValueSerializer(RedisSerializer.json());

        // hash的 value序列化方式采用 jdk
        redisTemplate.setHashValueSerializer(RedisSerializer.json());

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(redisKeySerializer());
        return redisTemplate;
    }


}
