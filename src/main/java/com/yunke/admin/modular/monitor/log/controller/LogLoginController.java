package com.yunke.admin.modular.monitor.log.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.common.util.DateUtil;
import com.yunke.admin.framework.core.annotion.OpLog;
import com.yunke.admin.framework.core.annotion.OpLogHeader;
import com.yunke.admin.framework.core.enums.OpLogAnnotionOpTypeEnum;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import com.yunke.admin.modular.monitor.log.model.entity.LogLogin;
import com.yunke.admin.modular.monitor.log.model.param.LogLoginDeleteParam;
import com.yunke.admin.modular.monitor.log.model.param.LogLoginPageQueryParam;
import com.yunke.admin.modular.monitor.log.model.vo.LogLoginVO;
import com.yunke.admin.modular.monitor.log.service.LogLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@OpLogHeader("登录日志")
@RestController
@RequestMapping("/monitor/log/")
@RequiredArgsConstructor
public class LogLoginController extends BaseController {


    private final LogLoginService logLoginService;

    @OpLog(title = "分页查询",opType = OpLogAnnotionOpTypeEnum.QUERY)
    @SaCheckPermission("monitor:log-login:list")
    @PostMapping(value = "logLoginPage")
    public ResponseData<PageWrapper> logLoginPage(@RequestBody LogLoginPageQueryParam visLogPageQueryParam){
        LambdaQueryWrapper<LogLogin> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 查询条件：关键字模糊查询 登录账号
        if(StrUtil.isNotEmpty(visLogPageQueryParam.getAccount())){
           lambdaQueryWrapper.like(LogLogin::getAccount,visLogPageQueryParam.getAccount());
        }

        // 查询条件：登录时间
        if(visLogPageQueryParam.getVisTimeStart() != null){
            Date date  = DateUtil.beginOfDay(visLogPageQueryParam.getVisTimeStart()).toJdkDate();
            lambdaQueryWrapper.ge(LogLogin::getVisTime, date);
        }
        if(visLogPageQueryParam.getVisTimeEnd() != null){
            Date date  = DateUtil.endOfDay(visLogPageQueryParam.getVisTimeEnd()).toJdkDate();
            lambdaQueryWrapper.le(LogLogin::getVisTime, date);
        }

        // 排序
        lambdaQueryWrapper.orderByDesc(LogLogin::getVisTime);
        Page<LogLogin> pageData = logLoginService.page(visLogPageQueryParam.page(), lambdaQueryWrapper);
        List<LogLoginVO> pageVOList =BeanUtil.copyListProperties(pageData.getRecords(), LogLoginVO::new);
        BeanUtil.fillListEnumDictField(pageVOList);
        PageWrapper pageWrapper = new PageWrapper(pageVOList,pageData.getTotal(), pageData.getSize(), pageData.getCurrent());
        return new SuccessResponseData<>(pageWrapper);
    }

    @OpLog(title = "删除",opType = OpLogAnnotionOpTypeEnum.DELETE)
    @SaCheckPermission("monitor:log-login:delete")
    @PostMapping(value = "deleteLogLogin")
    public ResponseData deleteLogLogin(@RequestBody LogLoginDeleteParam visLogDeleteParam){
        if(visLogDeleteParam.getIds() != null && visLogDeleteParam.getIds().size() > 0){
            logLoginService.removeByIds(visLogDeleteParam.getIds());
        }
        return ResponseData.success();
    }

    @OpLog(title = "清空",opType = OpLogAnnotionOpTypeEnum.CLEAN)
    @SaCheckPermission("monitor:log-login:clear")
    @GetMapping(value = "clearLogLogin")
    public ResponseData clearLogLogin(){
        logLoginService.removeAll();
        return ResponseData.success();
    }
}
