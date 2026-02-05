package com.yunke.admin.modular.system.cache.service;

import com.yunke.admin.modular.system.cache.model.vo.CacheCardVO;

import java.util.List;

public interface CacheManageService {

    List<CacheCardVO> getCardList();

    boolean refreshCache(String cacheType);

    boolean clearCache(String cacheType);

}
