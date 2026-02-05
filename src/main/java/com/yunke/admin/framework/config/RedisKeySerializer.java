package com.yunke.admin.framework.config;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class RedisKeySerializer implements RedisSerializer<String> {

    private final String keyPrefix;

    private final Charset charset;

    public RedisKeySerializer(String keyPrefix, Charset charset) {
        this.keyPrefix = keyPrefix;
        this.charset = charset;
    }

    public RedisKeySerializer(String keyPrefix) {
        this(keyPrefix, StandardCharsets.UTF_8);
    }

    @Override
    public byte[] serialize(String cacheKey) throws SerializationException {
        String key = keyPrefix + cacheKey;
        return key.getBytes(charset);

    }

    @Override
    public String deserialize(byte[] bytes) throws SerializationException {
        String cacheKey = new String(bytes, charset);
        if(cacheKey.indexOf(keyPrefix) == -1){
            cacheKey = keyPrefix + cacheKey;
        }
        return (cacheKey.getBytes() == null ? null : cacheKey);
    }
}
