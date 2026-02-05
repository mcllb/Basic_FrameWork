package com.yunke.admin.modular.system.dict.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yunke.admin.common.enums.CommonStatusEnum;
import com.yunke.admin.common.model.SingleDeleteParam;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.common.util.SaUtil;
import com.yunke.admin.framework.cache.base.CacheConstant;
import com.yunke.admin.framework.core.exception.ServiceException;
import com.yunke.admin.framework.eventbus.event.CacheEvent;
import com.yunke.admin.framework.mybatisplus.base.GeneralServiceImpl;
import com.yunke.admin.modular.system.dict.enums.DictTypeExceptionEnum;
import com.yunke.admin.modular.system.dict.mapper.DictTypeMapper;
import com.yunke.admin.modular.system.dict.model.entity.DictData;
import com.yunke.admin.modular.system.dict.model.entity.DictType;
import com.yunke.admin.modular.system.dict.model.param.DictTypeAddParam;
import com.yunke.admin.modular.system.dict.model.param.DictTypeEditParam;
import com.yunke.admin.modular.system.dict.model.param.DictTypeUpdateStatusParam;
import com.yunke.admin.modular.system.dict.model.param.DictUpdateSortParam;
import com.yunke.admin.modular.system.dict.service.DictDataService;
import com.yunke.admin.modular.system.dict.service.DictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @className DictTypeServiceImpl
 * @description: 系统字典类型表_服务实现类
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Service
public class DictTypeServiceImpl extends GeneralServiceImpl<DictTypeMapper, DictType> implements DictTypeService {

    @Autowired
    private DictDataService dictDataService;

    @Transactional
    @Override
    public boolean add(DictTypeAddParam dictTypeAddParam) {
        DictType dictType = new DictType();
        BeanUtil.copyProperties(dictTypeAddParam,dictType);
        // 校验参数
        checkParam(dictType,false);

        // 设置状态启用
        dictType.setStatus(CommonStatusEnum.ENABLE.getCode());
        return this.save(dictType);
    }

    @Transactional
    @Override
    public boolean edit(DictTypeEditParam dictTypeEditParam) {
        DictType dictType = new DictType();
        BeanUtil.copyProperties(dictTypeEditParam,dictType);
        // 校验参数
        checkParam(dictType,true);

        // 不修改状态
        dictType.setStatus(null);

        // 判断是否修改了字典类型编码，如果修改了类型编码需要重新加载缓存
        DictType oldDictType = this.getById(dictType.getId());
        boolean ret = this.updateById(dictType);
        if(ret){
            if(!oldDictType.getCode().equals(dictType.getCode())){
                dictDataService.lambdaUpdate()
                        .set(DictData::getTypeCode,dictType.getCode())
                        .set(DictData::getUpdateTime,new Date())
                        .set(DictData::getUpdateBy, SaUtil.getUserId())
                        .eq(DictData::getTypeCode,oldDictType.getCode())
                        .update();
                removeCache(oldDictType);

            }
            refreshCache(dictType);
        }
        return ret;
    }

    @Transactional
    @Override
    public boolean delete(SingleDeleteParam singleDeleteParam) {
        DictType dictType = this.getById(singleDeleteParam.getId());
        dictDataService.deleteByTypeId(singleDeleteParam.getId());
        boolean ret = this.removeById(singleDeleteParam.getId());
        if(ret){
            // 移除字典缓存
            removeCache(dictType);
        }
        return ret;
    }

    public void refreshCache(DictType dictType){
        CacheEvent cacheEvent = new CacheEvent();
        cacheEvent.setCacheName(CacheConstant.CACHE_NAME_DICT);
        cacheEvent.setKey(dictType.getCode());
        cacheEvent.setEventType(CacheEvent.EventType.REFRESH);
        publishEvent(cacheEvent);
    }

    private void publishEvent(CacheEvent event){
        SpringUtil.publishEvent(event);
    }

    public void removeCache(DictType dictType){
        CacheEvent cacheEvent = new CacheEvent();
        cacheEvent.setCacheName(CacheConstant.CACHE_NAME_DICT);
        cacheEvent.setKey(dictType.getCode());
        cacheEvent.setEventType(CacheEvent.EventType.REMOVE);
        publishEvent(cacheEvent);
    }

    @Transactional
    @Override
    public boolean updateDictTypeStatus(DictTypeUpdateStatusParam dictTypeUpdateStatusParam) {
        DictType dictType = new DictType();
        BeanUtil.copyProperties(dictTypeUpdateStatusParam, dictType);
        boolean ret = this.updateById(dictType);
        if(ret){
            dictType = this.getById(dictType.getId());
            // 修改状态启用时加载缓存，否则移除缓存
            if(dictType.getStatus().equals(CommonStatusEnum.ENABLE.getCode())){
                refreshCache(dictType);
            }else{
                removeCache(dictType);
            }
        }
        return ret;
    }

    @Override
    public boolean updateSort(DictUpdateSortParam updateSortParam) {
        DictType dictType = new DictType();
        BeanUtil.copyProperties(updateSortParam, dictType);
        return this.updateById(dictType);
    }

    @Override
    public void refreshCache(String id) {
        DictType dictType = this.getById(id);
        if(dictType != null){
            refreshCache(dictType);
        }
    }

    public void checkParam(DictType dictType,boolean isExcludeSelf){
        LambdaQueryWrapper<DictType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 校验字典名称是否重复
        lambdaQueryWrapper.eq(DictType::getName, dictType.getName());
        if(isExcludeSelf){
            lambdaQueryWrapper.ne(DictType::getId, dictType.getId());
        }
        int countName = this.count(lambdaQueryWrapper);
        if(countName > 0){
            throw new ServiceException(DictTypeExceptionEnum.DICT_TYPE_NAME_REPEAT);
        }

        // 校验字典编码是否重复
        lambdaQueryWrapper.clear();
        lambdaQueryWrapper.eq(DictType::getCode, dictType.getCode());
        if(isExcludeSelf){
            lambdaQueryWrapper.ne(DictType::getId, dictType.getId());
        }
        int countCode = this.count(lambdaQueryWrapper);
        if(countCode > 0){
            throw new ServiceException(DictTypeExceptionEnum.DICT_TYPE_CODE_REPEAT);
        }
    }
}
