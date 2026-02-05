package com.yunke.admin.modular.system.wx.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.framework.core.annotion.OpLog;
import com.yunke.admin.framework.core.annotion.OpLogHeader;
import com.yunke.admin.framework.core.enums.OpLogAnnotionOpTypeEnum;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import com.yunke.admin.modular.system.wx.model.param.WxUserAddParam;
import com.yunke.admin.modular.system.wx.model.param.WxUserEditParam;
import com.yunke.admin.modular.system.wx.model.param.WxUserPageQueryParam;
import com.yunke.admin.modular.system.wx.model.vo.WxUserVO;
import com.yunke.admin.modular.system.wx.service.WxUserService;
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
 * @className WxUserController
 * @description: 微信用户表_前端控制器
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/22
 */
@OpLogHeader("")
@RestController
@RequestMapping("/sys/wx/wxuser/")
@RequiredArgsConstructor
@Validated
public class WxUserController extends BaseController {

    private final WxUserService wxUserService;

    @OpLog(title = "分页查询",opType = OpLogAnnotionOpTypeEnum.QUERY)
    @SaCheckPermission("sys:wx-wxuser:page")
    @PostMapping(value = "page")
    public ResponseData<PageWrapper<WxUserVO>> page(@RequestBody WxUserPageQueryParam pageQueryParam){
        return new SuccessResponseData<>(wxUserService.pageVO(pageQueryParam));
    }

    @GetMapping(value = "detail")
    public ResponseData<WxUserVO> detail(@NotEmpty(message = "主键不能为空，请检查参数id") String id){
        return new SuccessResponseData<>(wxUserService.getVO(id));
    }

    @OpLog(title = "新增",opType = OpLogAnnotionOpTypeEnum.INSERT)
    @SaCheckPermission("sys:wx-wxuser:add")
    @PostMapping(value = "add")
    public ResponseData add(@Validated @RequestBody WxUserAddParam addParam){
        return ResponseData.bool(wxUserService.add(addParam));
    }

    @OpLog(title = "编辑",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @SaCheckPermission("sys:wx-wxuser:edit")
    @PostMapping(value = "edit")
    public ResponseData edit(@Validated @RequestBody WxUserEditParam editParam){
        return ResponseData.bool(wxUserService.edit(editParam));
    }

    @OpLog(title = "删除",opType = OpLogAnnotionOpTypeEnum.DELETE)
    @SaCheckPermission("sys:wx-wxuser:delete")
    @GetMapping(value = "delete")
    public ResponseData delete(@NotEmpty(message = "主键不能为空，请检查参数id") String id){
        return ResponseData.bool(wxUserService.delete(id));
    }

}
