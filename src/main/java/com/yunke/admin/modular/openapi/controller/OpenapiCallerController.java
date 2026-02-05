package com.yunke.admin.modular.openapi.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.framework.core.annotion.OpLog;
import com.yunke.admin.framework.core.annotion.OpLogHeader;
import com.yunke.admin.framework.core.enums.OpLogAnnotionOpTypeEnum;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import com.yunke.admin.modular.openapi.model.param.OpenapiCallerAddParam;
import com.yunke.admin.modular.openapi.model.param.OpenapiCallerChangeStatusParam;
import com.yunke.admin.modular.openapi.model.param.OpenapiCallerEditParam;
import com.yunke.admin.modular.openapi.model.param.OpenapiCallerPageQueryParam;
import com.yunke.admin.modular.openapi.model.vo.OpenapiCallerVO;
import com.yunke.admin.modular.openapi.service.OpenapiCallerService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotEmpty;
import java.lang.String;

/**
 * @className OpenapiCallerController
 * @description: 开放接口调用方管理_前端控制器
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@OpLogHeader("开放接口调用方管理")
@RestController
@RequestMapping("/openapi/caller/")
@RequiredArgsConstructor
@Validated
public class OpenapiCallerController extends BaseController {

    private final OpenapiCallerService apiAppService;

    @OpLog(title = "分页查询",opType = OpLogAnnotionOpTypeEnum.QUERY)
    @SaCheckPermission("openapi:caller:page")
    @PostMapping(value = "page")
    public ResponseData<PageWrapper<OpenapiCallerVO>> page(@RequestBody OpenapiCallerPageQueryParam pageQueryParam){
        return new SuccessResponseData<>(apiAppService.pageVO(pageQueryParam));
    }

    @GetMapping(value = "detail")
    public ResponseData<OpenapiCallerVO> detail(@NotEmpty(message = "主键不能为空，请检查参数id") String id){
        return new SuccessResponseData<>(apiAppService.getVO(id));
    }

    @OpLog(title = "新增",opType = OpLogAnnotionOpTypeEnum.INSERT)
    @SaCheckPermission("openapi:caller:add")
    @PostMapping(value = "add")
    public ResponseData add(@Validated @RequestBody OpenapiCallerAddParam addParam){
        return ResponseData.bool(apiAppService.add(addParam));
    }

    @OpLog(title = "编辑",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @SaCheckPermission("openapi:caller:edit")
    @PostMapping(value = "edit")
    public ResponseData edit(@Validated @RequestBody OpenapiCallerEditParam editParam){
        return ResponseData.bool(apiAppService.edit(editParam));
    }

    @OpLog(title = "删除",opType = OpLogAnnotionOpTypeEnum.DELETE)
    @SaCheckPermission("openapi:caller:delete")
    @GetMapping(value = "delete")
    public ResponseData delete(@NotEmpty(message = "主键不能为空，请检查参数id") String id){
        return ResponseData.bool(apiAppService.delete(id));
    }

    @OpLog(title = "修改状态",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @SaCheckPermission("openapi:caller:edit")
    @PostMapping(value = "changeStatus")
    public ResponseData changeStatus(@Validated @RequestBody OpenapiCallerChangeStatusParam changeStatusParam){
        return ResponseData.bool(apiAppService.changeStatus(changeStatusParam));
    }

}
