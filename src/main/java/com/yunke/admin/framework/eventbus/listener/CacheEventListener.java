package com.yunke.admin.framework.eventbus.listener;

import cn.hutool.core.map.MapUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.yunke.admin.framework.cache.base.AbstractBaseJetcache;
import com.yunke.admin.framework.eventbus.event.CacheEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class CacheEventListener {

    private static Map<String,AbstractBaseJetcache> CACHE_MAP = MapUtil.newConcurrentHashMap();

    @EventListener(CacheEvent.class)
    public void onApplicationEvent(CacheEvent event){
        log.debug("缓存事件监听:event={},data={}",event,event.getData());
        String cacheName = event.getCacheName();
        Assert.notNull(cacheName, "cacheName must not be null");

       if(event.getEventType() == CacheEvent.EventType.REMOVE){
            removeCache(cacheName,event.getKey());
        }else if(event.getEventType() == CacheEvent.EventType.REFRESH){
            refreshCache(cacheName,event.getKey());
        }else if(event.getEventType() == CacheEvent.EventType.LOAD){
            loadCache(cacheName);
        }
    }

    private AbstractBaseJetcache getCache(String cacheName){
        synchronized (cacheName){
            if(CACHE_MAP.containsKey(cacheName)){
                return CACHE_MAP.get(cacheName);
            }else{
                Map<String, AbstractBaseJetcache> beans = SpringUtil.getBeansOfType(AbstractBaseJetcache.class);
                if(MapUtil.isNotEmpty(beans)){
                    Optional<AbstractBaseJetcache> optional = beans.values().stream()
                            .filter(bean -> bean.getCacheName().equals(cacheName))
                            .findFirst();
                    if(optional.isPresent()){
                        AbstractBaseJetcache cache = optional.get();
                        CACHE_MAP.put(cacheName, cache);
                        return cache;
                    }
                }
            }
            return null;
        }
    }

    private void removeCache(String cacheName,String key){
        AbstractBaseJetcache cache = getCache(cacheName);
        if(cache == null){
            log.error("CacheEventListener removeCache fail not found cache cacheName={}",cacheName);
            return;
        }
        cache.remove(key);
        log.debug("CacheEventListener removeCache success,cacheName={},key={}",cacheName,key);
    }

    private void refreshCache(String cacheName,String key){
        AbstractBaseJetcache cache = getCache(cacheName);
        if(cache == null){
            log.error("CacheEventListener refreshCache fail not found cache,cacheName={}",cacheName);
            return;
        }
        cache.remove(key);
        log.debug("CacheEventListener refreshCache success,cacheName={}",cacheName);
    }

    private void loadCache(String cacheName){
        AbstractBaseJetcache cache = getCache(cacheName);
        if(cache == null){
            log.error("CacheEventListener loadCache fail not found cache,cacheName={}",cacheName);
            return;
        }
        cache.loadCache();
        log.debug("CacheEventListener loadCache success,cacheName={}",cacheName);
    }



}
