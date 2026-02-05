package com.yunke.admin.modular.openapi.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunke.admin.common.enums.CommonStatusEnum;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.common.util.SaUtil;
import com.yunke.admin.common.util.StrUtil;
import com.yunke.admin.framework.core.exception.ServiceException;
import com.yunke.admin.framework.mybatisplus.base.GeneralServiceImpl;
import com.yunke.admin.modular.openapi.mapper.OpenapiCallerMapper;
import com.yunke.admin.modular.openapi.model.entity.OpenapiCaller;
import com.yunke.admin.modular.openapi.model.param.OpenapiCallerAddParam;
import com.yunke.admin.modular.openapi.model.param.OpenapiCallerChangeStatusParam;
import com.yunke.admin.modular.openapi.model.param.OpenapiCallerEditParam;
import com.yunke.admin.modular.openapi.model.param.OpenapiCallerPageQueryParam;
import com.yunke.admin.modular.openapi.model.vo.OpenapiCallerVO;
import com.yunke.admin.modular.openapi.service.OpenapiCallerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @className OpenapiCallerServiceImpl
 * @description: 开放接口调用方管理_Service实现类
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Service
public class OpenapiCallerServiceImpl extends GeneralServiceImpl<OpenapiCallerMapper, OpenapiCaller> implements OpenapiCallerService {

    @Override
    public PageWrapper<OpenapiCallerVO> pageVO(OpenapiCallerPageQueryParam pageQueryParam) {
        LambdaQueryWrapper<OpenapiCaller> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 查询条件：关键字
        if(StrUtil.isNotBlank(pageQueryParam.getKeyword())){
            lambdaQueryWrapper.like(OpenapiCaller::getCallerName, pageQueryParam.getKeyword());
        }
        
        // 查询条件：状态
        if(StrUtil.isNotEmpty(pageQueryParam.getStatus())){
            lambdaQueryWrapper.eq(OpenapiCaller::getStatus, pageQueryParam.getStatus());
        }
        
        // 排序
        lambdaQueryWrapper.orderByDesc(OpenapiCaller::getCreateTime);

        Page<OpenapiCaller> pageData = this.page(pageQueryParam.page(), lambdaQueryWrapper);
        List<OpenapiCaller> records = pageData.getRecords();
        List<OpenapiCallerVO> voList = BeanUtil.copyListProperties(records, OpenapiCallerVO::new);
        PageWrapper<OpenapiCallerVO> pageWrapper = new PageWrapper<>(voList, pageData.getTotal(), pageData.getSize(), pageData.getCurrent());
        return pageWrapper;
    }
    
    @Override
    public OpenapiCallerVO getVO(String id){
        OpenapiCallerVO apiAppVO = null;
        OpenapiCaller apiApp = this.getById(id);
        if(apiApp != null){
        	apiAppVO = new OpenapiCallerVO();
            BeanUtil.copyProperties(apiApp,apiAppVO);
        }
        return apiAppVO;
    }

    @Transactional
    @Override
    public boolean add(OpenapiCallerAddParam addParam) {
        OpenapiCaller apiApp = new OpenapiCaller();
        BeanUtil.copyProperties(addParam,apiApp);
        checkParam(apiApp,false);
        apiApp.setCallerKey(generateAppKey());
        apiApp.setSecretKey(generateSecretKey());
        apiApp.setStatus(CommonStatusEnum.ENABLE.getCode());
        return this.save(apiApp);
    }

    @Transactional
    @Override
    public boolean edit(OpenapiCallerEditParam editParam) {
        OpenapiCaller apiApp = new OpenapiCaller();
        BeanUtil.copyProperties(editParam,apiApp);
        checkParam(apiApp,true);
        return this.updateById(apiApp);
    }

    @Transactional
    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    public boolean changeStatus(OpenapiCallerChangeStatusParam changeStatusParam) {
        return this.lambdaUpdate()
                .set(OpenapiCaller::getStatus,changeStatusParam.getStatus())
                .set(OpenapiCaller::getUpdateBy, SaUtil.getUserId())
                .set(OpenapiCaller::getUpdateTime,new Date())
                .eq(OpenapiCaller::getId,changeStatusParam.getId())
                .update();
    }

    private String generateAppKey(){
        String key = RandomUtil.randomString(RandomUtil.BASE_CHAR, 10);
        boolean exist = this.lambdaQuery().eq(OpenapiCaller::getCallerKey,key).count() > 0;
        if(!exist){
            return key;
        }
        return this.generateAppKey();
    }

    private String generateSecretKey(){
        String key = RandomUtil.randomString(32);
        boolean exist = this.lambdaQuery().eq(OpenapiCaller::getSecretKey,key).count() > 0;
        if(!exist){
            return key;
        }
        return this.generateSecretKey();
    }

    private void checkParam(OpenapiCaller entity, boolean excludeSelf){
        LambdaQueryWrapper<OpenapiCaller> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OpenapiCaller::getCallerName,entity.getCallerName());
        if(excludeSelf){
            lambdaQueryWrapper.ne(OpenapiCaller::getId, entity.getId());
        }
        int countAppName = this.count(lambdaQueryWrapper);
        if(countAppName > 0){
            throw new ServiceException("调用者名称重复了");
        }
    }

}
