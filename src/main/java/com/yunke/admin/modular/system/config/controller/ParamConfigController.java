package com.yunke.admin.modular.system.config.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.common.model.DetailQueryParam;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.common.model.SingleDeleteParam;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.framework.core.annotion.OpLog;
import com.yunke.admin.framework.core.annotion.OpLogHeader;
import com.yunke.admin.framework.core.enums.OpLogAnnotionOpTypeEnum;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import com.yunke.admin.modular.system.config.model.entity.ParamConfig;
import com.yunke.admin.modular.system.config.model.param.ParamConfigAddParam;
import com.yunke.admin.modular.system.config.model.param.ParamConfigEditParam;
import com.yunke.admin.modular.system.config.model.param.ParamConfigPageQueryParam;
import com.yunke.admin.modular.system.config.model.param.ParamConfigUpdateStatusParam;
import com.yunke.admin.modular.system.config.model.vo.ParamConfigPageVO;
import com.yunke.admin.modular.system.config.model.vo.ParamConfigVO;
import com.yunke.admin.modular.system.config.service.ParamConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @className ParamConfigController
 * @description: 参数配置表_前端控制器
 * <p>aaa</p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@OpLogHeader("系统参数管理")
@RestController
@RequestMapping("/sys/paramConfig/")
public class ParamConfigController extends BaseController {

    @Autowired
    private ParamConfigService paramConfigService;

    @OpLog(title = "分页查询",opType = OpLogAnnotionOpTypeEnum.QUERY)
    @SaCheckPermission("sys:param:list")
    @PostMapping(value = "page")
    public ResponseData<PageWrapper<ParamConfigPageVO>> page(@RequestBody ParamConfigPageQueryParam configPageQueryParam){
        LambdaQueryWrapper<ParamConfig> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 根据配置名称模糊查询
        if(StrUtil.isNotEmpty(configPageQueryParam.getConfigName())){
            lambdaQueryWrapper.like(ParamConfig::getConfigName, configPageQueryParam.getConfigName());
        }
        // 根据配置key模糊查询
        if(StrUtil.isNotEmpty(configPageQueryParam.getConfigKey())){
            lambdaQueryWrapper.like(ParamConfig::getConfigKey, configPageQueryParam.getConfigKey());
        }
        Page<ParamConfig> pageData = paramConfigService.page(configPageQueryParam.page(), lambdaQueryWrapper);
        List<ParamConfig> records = pageData.getRecords();
        List<ParamConfigPageVO> configPageVOList = BeanUtil.copyListProperties(records, ParamConfigPageVO::new);
        BeanUtil.fillListEnumDictField(configPageVOList);
        PageWrapper<ParamConfigPageVO> pageWrapper = new PageWrapper<>(configPageVOList,pageData.getTotal(),pageData.getSize(),pageData.getCurrent());
        return new SuccessResponseData(pageWrapper);
    }

    @OpLog(title = "新增",opType = OpLogAnnotionOpTypeEnum.INSERT)
    @SaCheckPermission("sys:param:add")
    @PostMapping(value = "add")
    public ResponseData add(@Validated @RequestBody ParamConfigAddParam configAddParam) {
        return ResponseData.bool(paramConfigService.add(configAddParam));
    }

    @OpLog(title = "编辑",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @SaCheckPermission("sys:param:edit")
    @PostMapping(value = "edit")
    public ResponseData edit(@Validated @RequestBody ParamConfigEditParam configEditParam) {
        return ResponseData.bool(paramConfigService.edit(configEditParam));
    }

    @OpLog(title = "删除",opType = OpLogAnnotionOpTypeEnum.DELETE)
    @SaCheckPermission("sys:param:delete")
    @PostMapping(value = "delete")
    public ResponseData delete(@Validated @RequestBody SingleDeleteParam singleDeleteParam) {
        return ResponseData.bool(paramConfigService.delete(singleDeleteParam));
    }

    @GetMapping(value = "detail")
    public ResponseData detail(@Validated DetailQueryParam detailQueryParam) {
        ParamConfig config = paramConfigService.getById(detailQueryParam.getId());
        ParamConfigVO configVO = new ParamConfigVO();
        BeanUtil.copyProperties(config,configVO);
        return new SuccessResponseData(configVO);
    }

    @OpLog(title = "修改状态",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @SaCheckPermission("sys:param:edit")
    @PostMapping(value = "changeStatus")
    public ResponseData changeStatus(@Validated @RequestBody ParamConfigUpdateStatusParam configUpdateStatusParam) {
        return ResponseData.bool(paramConfigService.updateConfigStatus(configUpdateStatusParam));
    }

}
