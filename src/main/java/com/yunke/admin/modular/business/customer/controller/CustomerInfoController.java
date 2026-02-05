package com.yunke.admin.modular.business.customer.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.framework.core.annotion.OpLog;
import com.yunke.admin.framework.core.annotion.OpLogHeader;
import com.yunke.admin.framework.core.enums.OpLogAnnotionOpTypeEnum;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import com.yunke.admin.modular.business.customer.model.param.CustomerInfoAddParam;
import com.yunke.admin.modular.business.customer.model.param.CustomerInfoEditParam;
import com.yunke.admin.modular.business.customer.model.param.CustomerInfoPageQueryParam;
import com.yunke.admin.modular.business.customer.model.vo.CustomerInfoVO;
import com.yunke.admin.modular.business.customer.service.CustomerInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

/**
 * @author mcllb
 * @version 1.0
 * @className CustomerInfoController
 * @description: 前端控制器
 * <p></p>
 * @date 2026-01-19
 */
@OpLogHeader("客户信息管理")
@RestController
@RequestMapping("/biz/customer-info/")
@RequiredArgsConstructor
@Validated
public class CustomerInfoController extends BaseController {

    private final CustomerInfoService customerInfoService;

    @OpLog(title = "分页查询", opType = OpLogAnnotionOpTypeEnum.QUERY)
    @SaCheckPermission("biz:customer-info:list")
    @PostMapping(value = "page")
    public ResponseData<PageWrapper<CustomerInfoVO>> page(@RequestBody CustomerInfoPageQueryParam pageQueryParam) {
        return new SuccessResponseData<>(customerInfoService.pageVO(pageQueryParam));
    }

    @OpLog(title = "详情", opType = OpLogAnnotionOpTypeEnum.DETAIL)
    @GetMapping(value = "detail")
    public ResponseData<CustomerInfoVO> detail(@NotEmpty String id) {
        return new SuccessResponseData<>(customerInfoService.getVO(id));
    }

    @OpLog(title = "新增", opType = OpLogAnnotionOpTypeEnum.INSERT)
    @SaCheckPermission("biz:customer-info:add")
    @PostMapping(value = "add")
    public ResponseData add(@Validated @RequestBody CustomerInfoAddParam addParam) {
        return ResponseData.bool(customerInfoService.add(addParam));
    }

    @OpLog(title = "编辑", opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @SaCheckPermission("biz:customer-info:edit")
    @PostMapping(value = "edit")
    public ResponseData edit(@Validated @RequestBody CustomerInfoEditParam editParam) {
        return ResponseData.bool(customerInfoService.edit(editParam));
    }

    @OpLog(title = "删除", opType = OpLogAnnotionOpTypeEnum.DELETE)
    @SaCheckPermission("biz:customer-info:delete")
    @GetMapping(value = "delete")
    public ResponseData delete(@NotEmpty String id) {
        return ResponseData.bool(customerInfoService.delete(id));
    }

    @GetMapping(value = "deptOptions")
    public ResponseData deptOptions() {
        return new SuccessResponseData<>(customerInfoService.getDeptOptions());
    }

}