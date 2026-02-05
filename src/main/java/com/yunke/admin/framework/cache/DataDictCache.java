package com.yunke.admin.framework.cache;

import cn.hutool.core.collection.CollUtil;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CachePenetrationProtect;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yunke.admin.common.enums.CommonStatusEnum;
import com.yunke.admin.common.model.DictVO;
import com.yunke.admin.framework.cache.base.AbstractBaseJetcache;
import com.yunke.admin.modular.system.dict.model.entity.DictData;
import com.yunke.admin.modular.system.dict.model.entity.DictType;
import com.yunke.admin.modular.system.dict.service.DictDataService;
import com.yunke.admin.modular.system.dict.service.DictTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class DataDictCache extends AbstractBaseJetcache<DictVO> {

    public static final String CACHE_NAME = CACHE_NAME_DICT;

    private static final String CACHE_TITLE = CACHE_TITLE_DICT;

    @Autowired
    private DictTypeService dictTypeService;
    @Autowired
    private DictDataService dictDataService;

    private DataDictCache self;

    @Autowired
    public void setCache(DataDictCache cache) {
        this.self = cache;
    }

    public DataDictCache() {
        super(CACHE_NAME,CACHE_TITLE);
    }

    public DictVO load(String key) {
        LambdaQueryWrapper<DictData> dictDataLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dictDataLambdaQueryWrapper.eq(DictData::getTypeCode, key);
        dictDataLambdaQueryWrapper.eq(DictData::getStatus, CommonStatusEnum.ENABLE.getCode());
        dictDataLambdaQueryWrapper.orderByAsc(DictData::getSort);
        List<DictData> dictDataList = dictDataService.list(dictDataLambdaQueryWrapper);
        if(CollUtil.isNotEmpty(dictDataList)){
            DictVO dict = new DictVO();
            dictDataList.stream().forEach(dictData -> {
                dict.set(dictData.getCode(), dictData.getValue());
            });
            return dict;
        }
        return null;
    }

    @CachePenetrationProtect
    @Cached(name=CACHE_NAME, key="#key", expire = 3600)
    @Override
    public DictVO get(String key) {
        DictVO dictVO = this.load(key);
        return dictVO;
    }

    @CacheUpdate(name=CACHE_NAME, key="#key", value="#value")
    @Override
    public void put(String key, DictVO value) {

    }

    @CacheInvalidate(name=CACHE_NAME, key="#key")
    @Override
    public void remove(String key) {

    }


    @Override
    public void loadCache() {
        LambdaQueryWrapper<DictType> dictTypeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dictTypeLambdaQueryWrapper.eq(DictType::getStatus, CommonStatusEnum.ENABLE.getCode());
        List<DictType> dictTypeList = dictTypeService.list(dictTypeLambdaQueryWrapper);
        if(dictTypeList != null && dictTypeList.size() > 0){
            dictTypeList.stream().forEach(dictType -> {
                LambdaQueryWrapper<DictData> dictDataLambdaQueryWrapper = new LambdaQueryWrapper<>();
                dictDataLambdaQueryWrapper.eq(DictData::getTypeId, dictType.getId());
                dictDataLambdaQueryWrapper.eq(DictData::getStatus, CommonStatusEnum.ENABLE.getCode());
                dictDataLambdaQueryWrapper.orderByAsc(DictData::getSort);
                List<DictData> dictDataList = dictDataService.list(dictDataLambdaQueryWrapper);
                if(CollUtil.isNotEmpty(dictDataList)){
                    DictVO dict = new DictVO();
                    dictDataList.stream().forEach(dictData -> {
                        dict.set(dictData.getCode(), dictData.getValue());
                    });
                    this.self.put(dictType.getCode(),dict);
                }
            });
        }
    }


    @Override
    public void init() {
        log.debug("DataDictCache init ====>");
        self.get("init");
    }

}
