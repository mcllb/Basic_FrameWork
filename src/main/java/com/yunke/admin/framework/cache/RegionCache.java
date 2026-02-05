package com.yunke.admin.framework.cache;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CachePenetrationProtect;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import com.yunke.admin.common.enums.CommonStatusEnum;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.framework.cache.base.AbstractBaseJetcache;
import com.yunke.admin.modular.system.region.model.entity.Region;
import com.yunke.admin.modular.system.region.model.vo.RegionVO;
import com.yunke.admin.modular.system.region.service.RegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class RegionCache extends AbstractBaseJetcache<RegionVO> {

    public static final String CACHE_NAME = CACHE_NAME_REGION;

    private static final String CACHE_TITLE = CACHE_TITLE_REGION;


    @Autowired
    private RegionService regionService;

    private RegionCache self;

    @Autowired
    public void setCache(RegionCache cache) {
        this.self = cache;
    }

    public RegionCache() {
        super(CACHE_NAME,CACHE_TITLE);
    }


    @CachePenetrationProtect
    @Cached(name=CACHE_NAME, key="#key", expire = 3600)
    @Override
    public RegionVO get(String key) {
        return load(key);
    }

    @CacheUpdate(name=CACHE_NAME, key="#key", value="#value")
    @Override
    public void put(String key, RegionVO value) {

    }

    @CacheInvalidate(name=CACHE_NAME, key="#key")
    @Override
    public void remove(String key) {

    }


    @Override
    public void init() {
        log.debug("RegionCache init ====>");
        self.get("init");
    }

    @Override
    public RegionVO load(String key) {
        Region region = regionService.lambdaQuery()
                .eq(Region::getEnable, CommonStatusEnum.ENABLE.getCode())
                .eq(Region::getId,key)
                .one();
        if(region != null){
            RegionVO regionVO = new RegionVO();
            BeanUtil.copyProperties(region,regionVO);
            return regionVO;
        }
        return null;
    }


    @Override
    public void loadCache() {
        List<Region> list = regionService.lambdaQuery().eq(Region::getEnable, CommonStatusEnum.ENABLE.getCode())
                .orderByAsc(Region::getId).list();
        List<RegionVO> voList = BeanUtil.copyListProperties(list, RegionVO::new);
        if(voList != null && voList.size() > 0){
            voList.forEach(item -> {
                this.self.put(item.getId(),item);
            });
        }
    }
}
