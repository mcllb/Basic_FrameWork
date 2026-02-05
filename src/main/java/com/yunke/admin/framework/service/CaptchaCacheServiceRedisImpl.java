package com.yunke.admin.framework.service;

import com.anji.captcha.properties.AjCaptchaProperties;
import com.anji.captcha.service.CaptchaCacheService;
import com.yunke.admin.framework.cache.CaptchaCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @className CaptchaCacheServiceRedisImpl
 * @description: 验证码实现类
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Slf4j
public class CaptchaCacheServiceRedisImpl implements CaptchaCacheService {

    @Autowired
    private CaptchaCache captchaCache;

    @Override
    public String type() {
        return AjCaptchaProperties.StorageType.redis.name();
    }

    @Override
    public void set(String key, String value, long expiresInSeconds) {
        log.debug("CaptchaCacheServiceRedisImpl====set===key={},value={}",key,value);
        captchaCache.put(key,value,expiresInSeconds);
    }

    @Override
    public boolean exists(String key) {
        boolean exist = captchaCache.exist(key);
        log.debug("CaptchaCacheServiceRedisImpl====exists===key={},exist={}",key,exist);
        return exist;
    }

    @Override
    public void delete(String key) {
        captchaCache.remove(key);
        log.debug("CaptchaCacheServiceRedisImpl====delete===key={}",key);
    }

    @Override
    public String get(String key) {
        String value = captchaCache.get(key);
        log.debug("CaptchaCacheServiceRedisImpl====get===key={},value={}",key,value);
        return value;
    }

}
