package com.yunke.admin.modular.system.dict.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yunke.admin.common.enums.CommonStatusEnum;
import com.yunke.admin.common.model.SingleDeleteParam;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.framework.core.exception.ServiceException;
import com.yunke.admin.framework.mybatisplus.base.GeneralServiceImpl;
import com.yunke.admin.modular.system.dict.enums.DictDataExceptionEnum;
import com.yunke.admin.modular.system.dict.mapper.DictDataMapper;
import com.yunke.admin.modular.system.dict.model.entity.DictData;
import com.yunke.admin.modular.system.dict.model.param.DictDataAddParam;
import com.yunke.admin.modular.system.dict.model.param.DictDataEditParam;
import com.yunke.admin.modular.system.dict.model.param.DictDataUpdateStatusParam;
import com.yunke.admin.modular.system.dict.model.param.DictUpdateSortParam;
import com.yunke.admin.modular.system.dict.service.DictDataService;
import com.yunke.admin.modular.system.dict.service.DictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @className DictDataServiceImpl
 * @description: 系统字典值表_服务实现类
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Service
public class DictDataServiceImpl extends GeneralServiceImpl<DictDataMapper, DictData> implements DictDataService {

    @Lazy
    @Autowired
    private DictTypeService dictTypeService;

    @Transactional
    @Override
    public boolean add(DictDataAddParam dictDataAddParam) {
        DictData dictData = new DictData();
        BeanUtil.copyProperties(dictDataAddParam,dictData);
        // 校验参数
        checkParam(dictData,false);

        // 设置状态启用
        dictData.setStatus(CommonStatusEnum.ENABLE.getCode());
        boolean ret = this.save(dictData);
        if(ret){
            // 加载字典缓存
            refreshCache(dictData);
        }
        return ret;
    }

    @Transactional
    @Override
    public boolean edit(DictDataEditParam dictDataEditParam) {
        DictData dictData = new DictData();
        BeanUtil.copyProperties(dictDataEditParam,dictData);
        // 校验参数
        checkParam(dictData,true);

        // 不修改状态
        dictData.setStatus(null);
        // 不修改类型编码
        dictData.setTypeCode(null);
        boolean ret = this.updateById(dictData);
        if(ret){
            // 加载字典缓存
            refreshCache(dictData);
        }
        return ret;
    }

    @Transactional
    @Override
    public boolean delete(SingleDeleteParam singleDeleteParam) {
        DictData dictData = this.getById(singleDeleteParam.getId());
        boolean ret = this.removeById(singleDeleteParam.getId());
        if(ret){
            // 刷新字典缓存
            refreshCache(dictData);
        }
        return ret;
    }

    /**
     * 刷新字典缓存
     * @param dictData
     */
    private void refreshCache(DictData dictData){
        dictTypeService.refreshCache(dictData.getTypeId());

    }

    @Transactional
    @Override
    public boolean deleteByTypeId(String typeId) {
        LambdaQueryWrapper<DictData> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DictData::getTypeId, typeId);
        return this.remove(lambdaQueryWrapper);
    }

    @Transactional
    @Override
    public boolean updateDictDataStatus(DictDataUpdateStatusParam dictDataUpdateStatusParam) {
        DictData dictData = new DictData();
        BeanUtil.copyProperties(dictDataUpdateStatusParam, dictData);
        boolean ret = this.updateById(dictData);
        if(ret){
            // 加载缓存
            dictData = this.getById(dictData.getId());
            refreshCache(dictData);
        }
        return ret;
    }

    @Override
    public boolean updateSort(DictUpdateSortParam updateSortParam) {
        DictData dictData = new DictData();
        BeanUtil.copyProperties(updateSortParam, dictData);
        return this.updateById(dictData);
    }


    public void checkParam(DictData dictData,boolean isExcludeSelf) {
        LambdaQueryWrapper<DictData> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 校验字典值编码是否重复
        lambdaQueryWrapper.eq(DictData::getCode, dictData.getCode());
        lambdaQueryWrapper.eq(DictData::getTypeId, dictData.getTypeId());
        if(isExcludeSelf){
            lambdaQueryWrapper.ne(DictData::getId, dictData.getId());
        }
        int countCode = this.count(lambdaQueryWrapper);
        if(countCode > 0){
            throw new ServiceException(DictDataExceptionEnum.DICT_TYPE_CODE_REPEAT);
        }

        // 校验字典值谁否重复
        lambdaQueryWrapper.clear();
        lambdaQueryWrapper.eq(DictData::getValue, dictData.getValue());
        lambdaQueryWrapper.eq(DictData::getTypeId, dictData.getTypeId());
        if(isExcludeSelf){
            lambdaQueryWrapper.ne(DictData::getId, dictData.getId());
        }
        int countValue = this.count(lambdaQueryWrapper);
        if(countValue > 0){
            throw new ServiceException(DictDataExceptionEnum.DICT_TYPE_VALUE_REPEAT);
        }
    }


}
