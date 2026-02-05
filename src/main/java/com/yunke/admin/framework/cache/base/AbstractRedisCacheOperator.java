package com.yunke.admin.framework.cache.base;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.yunke.admin.common.util.RedisUtil;
import com.yunke.admin.common.util.SaUtil;
import com.yunke.admin.framework.cache.CacheOperator;
import com.yunke.admin.framework.config.ProjectConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @className AbstractRedisCacheOperator
 * @description: 基于redis的缓存封装
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Slf4j
public abstract class AbstractRedisCacheOperator<T> implements CacheOperator<T> {

    @Autowired
    private RedisUtil redisUtil;

    private String redisKeyPrefix;

    public AbstractRedisCacheOperator() {
        ProjectConfig projectConfig = SpringUtil.getBean(ProjectConfig.class);
        if(StrUtil.isNotEmpty(projectConfig.getRedisKeyPrefix())){
            redisKeyPrefix = projectConfig.getRedisKeyPrefix();
        }else{
            redisKeyPrefix = SaUtil.getTokenName() + "-APP";
        }
    }

    /**
     * 获取完整的key前缀
     * @return
     */
    public String getKeyPrefix() {
        String prefix = this.redisKeyPrefix + ":" + getCommonKeyPrefix();
        return prefix;
    }

    @Override
    public void put(String key, T value) {
        redisUtil.set(getKeyPrefix() + key, value);
    }

    @Override
    public void put(String key, T value, Long timeoutSeconds) {
        redisUtil.set(getKeyPrefix() + key,value,timeoutSeconds);
    }

    @Override
    public T get(String key) {
        return (T) redisUtil.get(getKeyPrefix() + key);
    }

    @Override
    public void remove(String... key) {
        if(ObjectUtil.isNotNull(key) && key.length > 0){
            if(key.length == 1){
                redisUtil.del(getKeyPrefix() + key[0]);
            }else {
                List<String> keyList = CollUtil.newArrayList(key).stream().map(item -> getKeyPrefix()).collect(Collectors.toList());
                redisUtil.del(keyList);
            }
        }
    }

    @Override
    public void removeAll() {
        Set<String> allKeys = this.getAllKeys();
        if(allKeys != null && allKeys.size() > 0){
            allKeys.forEach(key -> {
                this.remove(key);
            });
        }
    }

    @Override
    public Set<String> getAllKeys() {
        Set<String> allKeys = redisUtil.getKeys(getKeyPrefix() + "*");
        if(CollUtil.isNotEmpty(allKeys)){
            // 去掉缓存key的common prefix前缀
            allKeys = allKeys.stream().map(item -> StrUtil.removePrefix(item,getKeyPrefix())).collect(Collectors.toSet());
        }
        return allKeys;
    }

    @Override
    public List<T> getAllValues() {
        List<T> values = CollUtil.newArrayList();
        Collection<String> allKeys = getAllKeys();
        if(CollUtil.isNotEmpty(allKeys)){
            allKeys.forEach(key -> {
                values.add(get(key));
            });
        }
        return values;
    }

    @Override
    public Map<String, T> getAllKeyValues() {
        Set<String> allKeys = this.getAllKeys();
        HashMap<String, T> results = MapUtil.newHashMap();
        for (String key : allKeys) {
            results.put(key, this.get(key));
        }
        return results;
    }


}
