package com.yunke.admin.modular.system.cache.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.yunke.admin.framework.cache.base.AbstractBaseJetcache;
import com.yunke.admin.framework.cache.base.CacheConstant;
import com.yunke.admin.modular.system.cache.model.vo.CacheCardVO;
import com.yunke.admin.modular.system.cache.service.CacheManageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CacheManageServiceImpl implements CacheManageService {



    @Override
    public List<CacheCardVO> getCardList() {

        List<CacheCardVO> list = CollUtil.newArrayList();

        Map<String, AbstractBaseJetcache> beans = SpringUtil.getBeansOfType(AbstractBaseJetcache.class);
        if(MapUtil.isNotEmpty(beans)){

            beans.values().stream().filter(bean -> {
                //排除登录验证码缓存
                return !CacheConstant.CACHE_NAME_CAPTCHA.equals(bean.getCacheName());
            }).forEach(cache -> {
                CacheCardVO cacheCardVO = new CacheCardVO();
                cacheCardVO.setCacheTitle(cache.getCacheTitle());
                cacheCardVO.setCacheType(cache.getCacheName());
                cacheCardVO.setCachaSize(cache.getAllKeys().size());
                list.add(cacheCardVO);
            });
        }
        return list;
    }

    @Override
    public boolean refreshCache(String cacheType) {
        Map<String, AbstractBaseJetcache> beans = SpringUtil.getBeansOfType(AbstractBaseJetcache.class);
        if(MapUtil.isNotEmpty(beans)){
            beans.values().stream()
                    .filter(bean -> bean.getCacheName().equals(cacheType))
                    .forEach(cache -> {
                        cache.removeAll();
                        cache.loadCache();
                    });
        }
        return true;
    }

    @Override
    public boolean clearCache(String cacheType) {
        Map<String, AbstractBaseJetcache> beans = SpringUtil.getBeansOfType(AbstractBaseJetcache.class);
        if(MapUtil.isNotEmpty(beans)){
            beans.values().stream()
                    .filter(bean -> bean.getCacheName().equals(cacheType))
                    .forEach(cache -> {
                        cache.removeAll();
                    });
        }
        return true;
    }
}
