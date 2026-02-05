package com.yunke.admin.modular.home.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.common.util.DateUtil;
import com.yunke.admin.common.util.SaUtil;
import com.yunke.admin.modular.home.model.param.UserLogLoginPageQueryParam;
import com.yunke.admin.modular.home.model.param.UserLogOperPageQueryParam;
import com.yunke.admin.modular.monitor.log.model.entity.LogLogin;
import com.yunke.admin.modular.monitor.log.model.entity.LogOper;
import com.yunke.admin.modular.monitor.log.model.vo.LogLoginVO;
import com.yunke.admin.modular.monitor.log.model.vo.LogOperVO;
import com.yunke.admin.modular.monitor.log.service.LogLoginService;
import com.yunke.admin.modular.monitor.log.service.LogOperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HomeService {

    @Autowired
    private LogLoginService logLoginService;
    @Autowired
    private LogOperService logOperService;

    public PageWrapper logLoginPage(UserLogLoginPageQueryParam  pageQueryParam){
        LambdaQueryWrapper<LogLogin> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 查询当前登录账号
        lambdaQueryWrapper.like(LogLogin::getAccount, SaUtil.getAccount());

        // 查询条件：登录时间
        if(pageQueryParam.getVisTimeStart() != null){
            Date date = DateUtil.beginOfDay(pageQueryParam.getVisTimeStart()).toJdkDate();
            lambdaQueryWrapper.ge(LogLogin::getVisTime, date);
        }
        if(pageQueryParam.getVisTimeEnd() != null){
            Date date = DateUtil.endOfDay(pageQueryParam.getVisTimeEnd()).toJdkDate();
            lambdaQueryWrapper.le(LogLogin::getVisTime, date);
        }

        // 排序
        lambdaQueryWrapper.orderByDesc(LogLogin::getVisTime);
        Page<LogLogin> pageData = logLoginService.page(pageQueryParam.page(), lambdaQueryWrapper);
        List<LogLoginVO> pageVOList = BeanUtil.copyListProperties(pageData.getRecords(), LogLoginVO::new);
        BeanUtil.fillListEnumDictField(pageVOList);
        PageWrapper pageWrapper = new PageWrapper(pageVOList,pageData.getTotal(), pageData.getSize(), pageData.getCurrent());
        return pageWrapper;
    }

    public PageWrapper logOperPage(UserLogOperPageQueryParam pageQueryParam){
        LambdaQueryWrapper<LogOper> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 查询当前登录账号
        lambdaQueryWrapper.like(LogOper::getUserId, SaUtil.getUserId());

        // 查询条件：计划操作时间
        if(pageQueryParam.getOpTimeStart() != null){
            Date date = cn.hutool.core.date.DateUtil.beginOfDay(pageQueryParam.getOpTimeStart()).toJdkDate();
            lambdaQueryWrapper.ge(LogOper::getOpTime, date);
        }
        if(pageQueryParam.getOpTimeEnd() != null){
            Date date = cn.hutool.core.date.DateUtil.endOfDay(pageQueryParam.getOpTimeEnd()).toJdkDate();
            lambdaQueryWrapper.le(LogOper::getOpTime, date);
        }

        // 排序
        lambdaQueryWrapper.orderByDesc(LogOper::getOpTime);
        Page<LogOper> pageData = logOperService.page(pageQueryParam.page(), lambdaQueryWrapper);
        List<LogOperVO> pageVOList = BeanUtil.copyListProperties(pageData.getRecords(), LogOperVO::new);
        BeanUtil.fillListEnumDictField(pageVOList);
        PageWrapper pageWrapper = new PageWrapper(pageVOList,pageData.getTotal(), pageData.getSize(), pageData.getCurrent());
        return pageWrapper;
    }

}
