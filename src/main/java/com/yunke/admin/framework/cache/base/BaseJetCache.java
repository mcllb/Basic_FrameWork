package com.yunke.admin.framework.cache.base;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.support.ConfigProvider;
import com.alicp.jetcache.anno.support.GlobalCacheConfig;
import org.springframework.core.env.Environment;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @className BaseJetCache
 * @description: JetCache操作基类
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
public interface BaseJetCache<T> {

    T load(String key);

    default void put(String key, T value){
        this.getCache().put(key,value);
    }

    default void put(String key, T value, long expireTime){
        this.getCache().put(key, value,expireTime, TimeUnit.SECONDS);
    }

    default void put(Map<String, T> map){
        this.getCache().putAll(map);
    }

    default void put(Map<String, T> map, long expireTime){
        this.getCache().putAll(map,expireTime,TimeUnit.SECONDS);
    }

    default T get(String key){
        return this.getCache().get(key);
    }

    default Map<String, T> get(String... keys){
        Map<String, T> ret = new LinkedHashMap();
        CollUtil.newHashSet(keys).forEach(key -> {
            ret.put(key,this.get(key));
        });
        return ret;
    }


    default void remove(String key){
        this.getCache().remove(key);
    }

    default void remove(String... keys){
        this.getCache().removeAll(CollUtil.newHashSet(keys));
    }

    default boolean exist(String key){
        return this.get(key) != null;
    }

    Cache<String, T> getCache();

    default String getCommonKeyPrefix(){
        ConfigProvider configProvider = SpringUtil.getBean(ConfigProvider.class);
        GlobalCacheConfig globalCacheConfig = configProvider.getGlobalCacheConfig();
        Environment environment = SpringUtil.getBean(Environment.class);
        String keyPrefix = environment.getProperty("jetcache.remote.default.keyPrefix");
        if(globalCacheConfig.isAreaInCacheName()){
            keyPrefix += "default_";
        }
        keyPrefix += this.getCacheName();
        return keyPrefix;
    }

    String getCacheName();

    void loadCache();

    /**
     * 删除全部缓存
     */
    void removeAll();

    void init();

}
