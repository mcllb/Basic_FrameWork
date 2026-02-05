package com.yunke.admin.modular.monitor.log.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.framework.core.annotion.OpLog;
import com.yunke.admin.framework.core.annotion.OpLogHeader;
import com.yunke.admin.framework.core.enums.OpLogAnnotionOpTypeEnum;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import com.yunke.admin.modular.monitor.log.model.entity.LogOper;
import com.yunke.admin.modular.monitor.log.model.param.LogOperDeleteParam;
import com.yunke.admin.modular.monitor.log.model.param.LogOperPageQueryParam;
import com.yunke.admin.modular.monitor.log.model.vo.LogOperVO;
import com.yunke.admin.modular.monitor.log.service.LogOperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@OpLogHeader("操作日志")
@RestController
@RequestMapping("/monitor/log/")
@RequiredArgsConstructor
public class LogOperController extends BaseController {

    private final LogOperService logOperService;

    @SaCheckPermission("monitor:log-oper:list")
    @PostMapping(value = "logOperPage")
    public ResponseData<PageWrapper> logOperPage(@RequestBody LogOperPageQueryParam operLogPageQueryParam){
        LambdaQueryWrapper<LogOper> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 查询条件：关键字模糊查询 操作人账号 操作人名称
        if(StrUtil.isNotEmpty(operLogPageQueryParam.getKeyword())){
            lambdaQueryWrapper.and(wq -> wq.like(LogOper::getAccount, operLogPageQueryParam.getKeyword())
                    .or().like(LogOper::getUserName, operLogPageQueryParam.getKeyword()));
        }

        // 查询条件：计划操作时间
        if(operLogPageQueryParam.getOpTimeStart() != null){
            Date date = DateUtil.beginOfDay(operLogPageQueryParam.getOpTimeStart()).toJdkDate();
            lambdaQueryWrapper.ge(LogOper::getOpTime, date);
        }
        if(operLogPageQueryParam.getOpTimeEnd() != null){
            Date date = DateUtil.endOfDay(operLogPageQueryParam.getOpTimeEnd()).toJdkDate();
            lambdaQueryWrapper.le(LogOper::getOpTime, date);
        }

        // 排序
        lambdaQueryWrapper.orderByDesc(LogOper::getOpTime);
        Page<LogOper> pageData = logOperService.page(operLogPageQueryParam.page(), lambdaQueryWrapper);
        List<LogOperVO> pageVOList = BeanUtil.copyListProperties(pageData.getRecords(), LogOperVO::new);
        BeanUtil.fillListEnumDictField(pageVOList);
        PageWrapper pageWrapper = new PageWrapper(pageVOList,pageData.getTotal(), pageData.getSize(), pageData.getCurrent());
        return new SuccessResponseData<>(pageWrapper);
    }

    @OpLog(title = "删除",opType = OpLogAnnotionOpTypeEnum.DELETE)
    @SaCheckPermission("monitor:log-oper:delete")
    @PostMapping(value = "deleteLogOper")
    public ResponseData deleteLogOper(@RequestBody LogOperDeleteParam operLogDeleteParam){
        if(operLogDeleteParam.getIds() != null && operLogDeleteParam.getIds().size() > 0){
            logOperService.removeByIds(operLogDeleteParam.getIds());
        }
        return ResponseData.success();
    }

    @OpLog(title = "清空",opType = OpLogAnnotionOpTypeEnum.CLEAN)
    @SaCheckPermission("monitor:log-oper:clear")
    @GetMapping(value = "clearLogOper")
    public ResponseData clearLogOper(){
        logOperService.removeAll();
        return ResponseData.success();
    }


}
