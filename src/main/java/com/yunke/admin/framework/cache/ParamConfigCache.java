package com.yunke.admin.framework.cache;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CachePenetrationProtect;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yunke.admin.common.enums.CommonStatusEnum;
import com.yunke.admin.framework.cache.base.AbstractBaseJetcache;
import com.yunke.admin.modular.system.config.model.entity.ParamConfig;
import com.yunke.admin.modular.system.config.service.ParamConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class ParamConfigCache extends AbstractBaseJetcache<ParamConfig> {

    public static final String CACHE_NAME = CACHE_NAME_PARAM_CONFIG;

    private static final String CACHE_TITLE = CACHE_TITLE_PARAM_CONFIG;

    @Autowired
    private ParamConfigService paramConfigService;

    private ParamConfigCache self;

    @Autowired
    public void setCache(ParamConfigCache cache) {
        this.self = cache;
    }

    public ParamConfigCache() {
        super(CACHE_NAME,CACHE_TITLE);
    }

    @CachePenetrationProtect
    @Cached(name=CACHE_NAME, key="#key", expire = 3600)
    @Override
    public ParamConfig get(String key) {
        return load(key);
    }

    @CacheUpdate(name=CACHE_NAME, key="#key", value="#value")
    @Override
    public void put(String key, ParamConfig value) {

    }

    @CacheInvalidate(name=CACHE_NAME, key="#key")
    @Override
    public void remove(String key) {

    }


    @Override
    public void init() {
        log.debug("ParamConfigCache init ====>");
        self.get("init");
    }

    @Override
    public ParamConfig load(String key) {
        return paramConfigService.lambdaQuery()
                .eq(ParamConfig::getStatus, CommonStatusEnum.ENABLE.getCode())
                .eq(ParamConfig::getConfigKey,key)
                .one();
    }


    @Override
    public void loadCache() {
        LambdaQueryWrapper<ParamConfig> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ParamConfig::getStatus, CommonStatusEnum.ENABLE.getCode());
        List<ParamConfig> list = paramConfigService.list(lambdaQueryWrapper);
        if(list != null && list.size() > 0){
            list.stream().forEach(config -> {
                this.self.put(config.getConfigKey(),config);
            });
        }
    }
}
