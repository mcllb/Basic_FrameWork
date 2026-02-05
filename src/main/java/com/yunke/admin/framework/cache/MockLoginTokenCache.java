package com.yunke.admin.framework.cache;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CachePenetrationProtect;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import com.yunke.admin.framework.cache.base.AbstractBaseJetcache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class MockLoginTokenCache extends AbstractBaseJetcache<String> {

    public static final String CACHE_NAME = CACHE_NAME_MOCK_LOGIN_TOKEN;

    private static final String CACHE_TITLE = CACHE_TITLE_MOCK_LOGIN_TOKEN;

    @Autowired
    public void setSelf(MockLoginTokenCache self) {
        this.self = self;
    }

    private MockLoginTokenCache self;

    public MockLoginTokenCache() {
        super(CACHE_NAME,CACHE_TITLE);
    }

    @Override
    public String load(String key) {
        return key;
    }

    @CachePenetrationProtect
    @Cached(name=CACHE_NAME, key="#key", expire = 60)
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
    public void loadCache() {

    }

    @Override
    public void init() {
        log.debug("MockLoginTokenCache init ====>");
        self.get("init");
    }
}
