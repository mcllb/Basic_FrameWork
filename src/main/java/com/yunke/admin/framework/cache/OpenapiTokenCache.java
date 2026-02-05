package com.yunke.admin.framework.cache;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CachePenetrationProtect;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import com.yunke.admin.framework.cache.base.AbstractBaseJetcache;
import com.yunke.admin.modular.openapi.model.entity.OpenapiCaller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class OpenapiTokenCache extends AbstractBaseJetcache<OpenapiCaller> {

    public static final String CACHE_NAME = "OPENAPI_TOKEN:";

    private static final String CACHE_TITLE = "开放接口token缓存";

    private OpenapiTokenCache self;

    @Autowired
    public void setCache(OpenapiTokenCache cache) {
        this.self = cache;
    }

    public OpenapiTokenCache() {
        super(CACHE_NAME,CACHE_TITLE);
    }

    @Override
    public OpenapiCaller load(String key) {
        return null;
    }

    @CachePenetrationProtect
    @Cached(name=CACHE_NAME, key="#key", expire = 7200)
    @Override
    public OpenapiCaller get(String key) {
        return null;
    }

    @CacheUpdate(name=CACHE_NAME, key="#key", value="#value")
    @Override
    public void put(String key, OpenapiCaller value) {

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
        log.debug("OpenapiTokenCache init ====>");
        self.get("init");
    }

}
