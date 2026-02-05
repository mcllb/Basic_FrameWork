package com.yunke.admin.framework.cache;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CachePenetrationProtect;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import com.yunke.admin.framework.cache.base.AbstractBaseJetcache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class CaptchaCache extends AbstractBaseJetcache<String> {

    public static final String CACHE_NAME = CACHE_NAME_CAPTCHA;

    private static final String CACHE_TITLE = CACHE_TITLE_CAPTCHA;

    @Autowired
    public void setSelf(CaptchaCache self) {
        this.self = self;
    }

    private CaptchaCache self;

    public CaptchaCache() {
        super(CACHE_NAME,CACHE_TITLE);
    }

    @CachePenetrationProtect
    @Cached(name=CACHE_NAME, key="#key", expire = 90)
    @Override
    public String get(String key) {
        return null;
    }

    @CacheUpdate(name=CACHE_NAME, key="#key", value="#value")
    @Override
    public void put(String key, String value) {

    }

    @CacheInvalidate(name=CACHE_NAME, key="#key")
    @Override
    public void remove(String key) {

    }

    @Override
    public void init() {
        log.debug("CaptchaCache init ====>");
        self.get("init");
    }

    @Override
    public String load(String key) {
        return null;
    }

    @Override
    public void loadCache() {

    }

}
