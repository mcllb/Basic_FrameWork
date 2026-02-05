package com.yunke.admin.modular.system.config.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yunke.admin.common.enums.CommonStatusEnum;
import com.yunke.admin.common.model.SingleDeleteParam;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.framework.cache.base.CacheConstant;
import com.yunke.admin.framework.core.exception.ServiceException;
import com.yunke.admin.framework.eventbus.event.CacheEvent;
import com.yunke.admin.framework.mybatisplus.base.GeneralServiceImpl;
import com.yunke.admin.modular.system.config.enums.ParamConfigExceptionEnum;
import com.yunke.admin.modular.system.config.mapper.ParamConfigMapper;
import com.yunke.admin.modular.system.config.model.entity.ParamConfig;
import com.yunke.admin.modular.system.config.model.param.ParamConfigAddParam;
import com.yunke.admin.modular.system.config.model.param.ParamConfigEditParam;
import com.yunke.admin.modular.system.config.model.param.ParamConfigUpdateStatusParam;
import com.yunke.admin.modular.system.config.service.ParamConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 参数配置表 服务实现类
 * </p>
 *
 * @author tianlei
 * @since 2021-01-06
 */
@Service
public class ParamConfigServiceImpl extends GeneralServiceImpl<ParamConfigMapper, ParamConfig> implements ParamConfigService {

    @Transactional
    @Override
    public boolean add(ParamConfigAddParam configAddParam) {
        ParamConfig config = new ParamConfig();
        BeanUtil.copyProperties(configAddParam, config);
        // 校验参数
        checkParam(config,false);
        // 设置状态为启用
        config.setStatus(CommonStatusEnum.ENABLE.getCode());
        boolean ret = this.save(config);
        return ret;
    }

    @Transactional
    @Override
    public boolean edit(ParamConfigEditParam configEditParam) {
        ParamConfig config = new ParamConfig();
        BeanUtil.copyProperties(configEditParam, config);
        // 校验参数
        checkParam(config,true);
        // 不修改状态
        config.setStatus(null);
        boolean ret = this.updateById(config);
        if(ret){
            refreshCache(config);
        }
        return ret;
    }

    public void refreshCache(ParamConfig config){
        CacheEvent cacheEvent = new CacheEvent();
        cacheEvent.setCacheName(CacheConstant.CACHE_NAME_PARAM_CONFIG);
        cacheEvent.setKey(config.getConfigKey());
        cacheEvent.setEventType(CacheEvent.EventType.REFRESH);
        SpringUtil.publishEvent(cacheEvent);
    }

    public void removeCahce(ParamConfig config){
        CacheEvent cacheEvent = new CacheEvent();
        cacheEvent.setCacheName(CacheConstant.CACHE_NAME_PARAM_CONFIG);
        cacheEvent.setKey(config.getConfigKey());
        cacheEvent.setEventType(CacheEvent.EventType.REMOVE);
        SpringUtil.publishEvent(cacheEvent);
    }

    @Transactional
    @Override
    public boolean delete(SingleDeleteParam singleDeleteParam) {
        ParamConfig config = this.getById(singleDeleteParam.getId());
        boolean ret = this.removeById(singleDeleteParam.getId());
        if(ret){
            // 清除缓存
            removeCahce(config);
        }
        return ret;
    }

    @Transactional
    @Override
    public boolean updateConfigStatus(ParamConfigUpdateStatusParam configUpdateStatusParam) {
        ParamConfig config = new ParamConfig();
        BeanUtil.copyProperties(configUpdateStatusParam, config);
        boolean ret = this.updateById(config);
        if(ret){
            config = this.getById(config.getId());
            // 修改状态为启用时加载缓存，否则清除缓存
            if(config.getStatus().equals(CommonStatusEnum.ENABLE.getCode())){
                refreshCache(config);
            }else{
                removeCahce(config);
            }
        }
        return ret;
    }


    public void checkParam(ParamConfig config, boolean isExcludeSelf) {
        LambdaQueryWrapper<ParamConfig> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 校验参数名称是否重复
        lambdaQueryWrapper.eq(ParamConfig::getConfigName, config.getConfigName());
        if (isExcludeSelf) {
            lambdaQueryWrapper.ne(ParamConfig::getId, config.getId());
        }
        int countName = this.count(lambdaQueryWrapper);
        if (countName > 0) {
            throw new ServiceException(ParamConfigExceptionEnum.CONFIG_NAME_REPEAT);
        }

        // 校验参数键名是否重复
        lambdaQueryWrapper.clear();
        lambdaQueryWrapper.eq(ParamConfig::getConfigKey, config.getConfigKey());
        if(isExcludeSelf){
            lambdaQueryWrapper.ne(ParamConfig::getId, config.getId());
        }
        int countKey = this.count(lambdaQueryWrapper);
        if(countKey > 0){
            throw new ServiceException(ParamConfigExceptionEnum.CONFIG_KEY_REPEAT);
        }
    }
}
