
package com.yunke.admin.framework.cache.base;

import cn.hutool.cache.impl.CacheObj;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.yunke.admin.framework.cache.CacheOperator;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @className AbstractMemoryCacheOperator
 * @description: 基于内存的缓存封装
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Slf4j
public abstract class AbstractMemoryCacheOperator<T> implements CacheOperator<T> {

    private final TimedCache<String, T> timedCache;

    public AbstractMemoryCacheOperator(TimedCache<String, T> timedCache) {
        this.timedCache = timedCache;
    }

    @Override
    public void put(String key, T value) {
        timedCache.put(getCommonKeyPrefix() + key, value);
    }

    @Override
    public void put(String key, T value, Long timeoutSeconds) {
        timedCache.put(getCommonKeyPrefix() + key, value, timeoutSeconds * 1000);
    }

    @Override
    public T get(String key) {
        // 如果用户在超时前调用了get(key)方法，会重头计算起始时间，false的作用就是不从头算
        return timedCache.get(getCommonKeyPrefix() + key, true);
    }

    @Override
    public void remove(String... key) {
        if (key.length > 0) {
            for (String itemKey : key) {
                timedCache.remove(getCommonKeyPrefix() + itemKey);
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
        Iterator<CacheObj<String, T>> cacheObjIterator = timedCache.cacheObjIterator();
        Set<String> keys = CollUtil.newHashSet();
        while (cacheObjIterator.hasNext()) {
            // 去掉缓存key的common prefix前缀
            String key = cacheObjIterator.next().getKey();
            keys.add(StrUtil.removePrefix(key, getCommonKeyPrefix()));
        }
        return keys;
    }

    @Override
    public List<T> getAllValues() {
        Iterator<CacheObj<String, T>> cacheObjIterator = timedCache.cacheObjIterator();
        ArrayList<T> values = CollectionUtil.newArrayList();
        while (cacheObjIterator.hasNext()) {
            values.add(cacheObjIterator.next().getValue());
        }
        return values;
    }

    @Override
    public Map<String, T> getAllKeyValues() {
        Collection<String> allKeys = this.getAllKeys();
        HashMap<String, T> results = MapUtil.newHashMap();
        for (String key : allKeys) {
            results.put(key, this.get(key));
        }
        return results;
    }
}
