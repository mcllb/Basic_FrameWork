package com.yunke.admin.framework.job;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xxl.job.core.context.XxlJobHelper;
import com.yunke.admin.common.constant.SysConfigKeyConstant;
import com.yunke.admin.common.util.DateUtil;
import com.yunke.admin.framework.core.util.SysConfigUtil;
import com.yunke.admin.modular.monitor.log.model.entity.LogLogin;
import com.yunke.admin.modular.monitor.log.model.entity.LogOper;
import com.yunke.admin.modular.monitor.log.service.LogLoginService;
import com.yunke.admin.modular.monitor.log.service.LogOperService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


public class SysLogClear {

    /**
     * @description: 清理登录日志
     * <p></p>
     * @param
     * @return void
     * @auth: tianlei
     * @date: 2026/1/14 15:18
     */
    @Transactional
    public void clearVisLog(){
        Integer days = SysConfigUtil.getSysConfigValue(SysConfigKeyConstant.SYSTEM_LOG_RETENTION_DAYS, Integer.class, 90);
        Date date = DateUtil.offsetDay(DateUtil.date(), -days).toJdkDate();
        LogLoginService visLogService = SpringUtil.getBean(LogLoginService.class);
        LambdaQueryWrapper<LogLogin> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.lt(LogLogin::getVisTime,date);
        visLogService.remove(lambdaQueryWrapper);
    }

    /**
     * @description: 清理操作日志
     * <p></p>
     * @return void
     * @auth: tianlei
     * @date: 2026/1/14 15:18
     */
    @Transactional
    public void clearOperLog(){
        Integer days = SysConfigUtil.getSysConfigValue(SysConfigKeyConstant.SYSTEM_LOG_RETENTION_DAYS, Integer.class, 90);
        Date date = DateUtil.offsetDay(DateUtil.date(), -days).toJdkDate();
        LogOperService operLogService = SpringUtil.getBean(LogOperService.class);
        LambdaQueryWrapper<LogOper> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.lt(LogOper::getOpTime, date);
        operLogService.remove(lambdaQueryWrapper);
    }

}
