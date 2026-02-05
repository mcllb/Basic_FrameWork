package com.yunke.admin.framework.cache.base;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheManager;
import com.alicp.jetcache.anno.support.ConfigProvider;
import com.alicp.jetcache.anno.support.GlobalCacheConfig;
import com.yunke.admin.common.constant.CommonConstant;
import com.yunke.admin.common.util.RedisUtil;
import com.yunke.admin.framework.config.ProjectConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractBaseJetcache<T> implements CacheConstant {

    private RedisUtil redisUtil;
    private CacheManager cacheManager;

    private String cacheName;

    private String cacheTitle;

    private String keyPrefix;

    @Autowired
    private ProjectConfig projectConfig;

    public AbstractBaseJetcache(String cacheName,String cacheTitle) {
        this.cacheName = cacheName;
        this.cacheTitle = cacheTitle;

        ConfigProvider configProvider = SpringUtil.getBean(ConfigProvider.class);
        GlobalCacheConfig globalCacheConfig = configProvider.getGlobalCacheConfig();
        Environment environment = SpringUtil.getBean(Environment.class);
        String keyPrefix = environment.getProperty("jetcache.remote.default.keyPrefix");
        if(globalCacheConfig.isAreaInCacheName()){
            keyPrefix += "default_";
        }
        keyPrefix += cacheName;
        this.keyPrefix = keyPrefix;


        this.redisUtil = SpringUtil.getBean(RedisUtil.class);
        this.cacheManager = SpringUtil.getBean(CacheManager.class);
    }

    public abstract T get(String key);

    public Map<String, T> get(String... keys){
        return this.get(CollUtil.newHashSet(keys));
    }

    public Map<String, T> get(List<String> keys){
        return getCache().getAll(CollUtil.newHashSet(keys));
    }

    public Map<String, T> get(Set<String> keys){
        return getCache().getAll(keys);
    }

    public abstract void put(String key, T value);

    public void put(String key, T value, long expireTime){
        getCache().put(key, value,expireTime, TimeUnit.SECONDS);
    }

    public void put(Map<String, T> map){
        getCache().putAll(map);
    }

    public void put(Map<String, T> map, long expireTime){
        getCache().putAll(map,expireTime,TimeUnit.SECONDS);
    }

    public abstract void remove(String key);

    public void remove(String... keys){
        getCache().removeAll(CollUtil.newHashSet(keys));
    }

    public void remove(List<String> keys){
        getCache().removeAll(CollUtil.newHashSet(keys));
    }

    public void remove(Set<String> keys){
        getCache().removeAll(keys);
    }


    public String getKeyPrefix(){
        return keyPrefix;
    }

    public boolean exist(String key){
        return getCache().get(key) != null;
    }

    public Set<String> getAllKeys(){
        String keyPrefix = getKeyPrefix();
        String searchKey = StrUtil.removePrefix(keyPrefix, projectConfig.getRedisKeyPrefix() + CommonConstant.REDIS_KEY_SEPARATOR);
        Set<String> allKeys = redisUtil.getKeys(searchKey + "*");
        if(CollUtil.isNotEmpty(allKeys)){
            // 去掉缓存key的common prefix前缀
            allKeys = allKeys.stream().map(item -> StrUtil.removePrefix(item,keyPrefix)).collect(Collectors.toSet());
        }
        return allKeys;
    }

    public List<T> getAllValues() {
        List<T> values = CollUtil.newArrayList();
        Set<String> allKeys = getAllKeys();
        if(CollUtil.isNotEmpty(allKeys)){
            if(allKeys.size() > 200){
                List<List<String>> keySplit = CollUtil.split(allKeys, 200);
                for(List<String> keys : keySplit){
                    Map<String, T> cacheMap = this.get(keys);
                    if(MapUtil.isNotEmpty(cacheMap)){
                        cacheMap.values().forEach(val -> {
                            if(val != null){
                                values.add(val);
                            }
                        });
                    }
                }
            }else{
                Map<String, T> cacheMap = this.get(allKeys);
                if(MapUtil.isNotEmpty(cacheMap)){
                    cacheMap.values().forEach(val -> {
                        if(val != null){
                            values.add(val);
                        }
                    });
                }
            }
        }
        return values;
    }

    public Map<String, T> getAllKeyValues() {
        Set<String> allKeys = this.getAllKeys();
        return this.get(allKeys);
    }

    public void removeAll(){
        this.clear();
    }


    public void clear() {
        Set<String> allKeys = this.getAllKeys();
        if(CollUtil.isNotEmpty(allKeys)){
            this.remove(allKeys);
        }
    }

    public String getCacheName(){
        return cacheName;
    }

    public String getCacheTitle(){
        return cacheTitle;
    }

    public Cache<String,T> getCache(){
        Cache<String,T> cache = cacheManager.getCache(cacheName);
        return cache;
    }

    @PostConstruct
    public void initCache(){
        log.debug("初始化缓存 cacheName={},cacheTitle={},keyPrefix={}",getCacheName(),getCacheTitle(),getKeyPrefix());
        init();
    };

    public abstract void init();

    public abstract T load(String key);

    public abstract void loadCache();

}
