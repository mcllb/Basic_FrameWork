package com.yunke.admin.modular.business.wx.sys.controller;

import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.framework.core.annotion.OpLog;
import com.yunke.admin.framework.core.annotion.OpLogHeader;
import com.yunke.admin.framework.core.annotion.WxUserCheck;
import com.yunke.admin.framework.core.enums.OpLogAnnotionOpTypeEnum;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import com.yunke.admin.modular.business.repair.model.param.RepairInfoEditParam;
import com.yunke.admin.modular.business.repair.model.vo.RepairInfoVO;
import com.yunke.admin.modular.business.repair.service.RepairInfoService;
import com.yunke.admin.modular.business.wx.cust.model.param.UploadFileParam;
import com.yunke.admin.modular.business.wx.sys.model.param.SysRepairInfoPageQueryParam;
import com.yunke.admin.modular.business.wx.sys.model.vo.SysRepairInfoListRspVO;
import com.yunke.admin.modular.business.wx.sys.service.WxSysCustomerInfoService;
import com.yunke.admin.modular.system.file.model.param.FileUploadParam;
import com.yunke.admin.modular.system.file.service.FileUploadService;
import com.yunke.admin.modular.system.wx.enums.WxUserTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

/**
  * @className WxCustomerInfoController
  * @description 微信端维修组信息_前端控制器
  * <p></p>
  * @version 1.0
  * @author tianlei
  * @date 2026/1/22
  */
@OpLogHeader("微信端维修组信息")
@RestController
@RequestMapping("/wx/sys/customer-info/")
@RequiredArgsConstructor
@Validated
@Api(tags = "微信维修端接口")
public class WxSysCustomerInfoController extends BaseController {

    private final WxSysCustomerInfoService wxSysCustomerInfoService;

    private final FileUploadService fileUploadService;

    private final RepairInfoService repairInfoService;

    @WxUserCheck(userType = WxUserTypeEnum.SYS)
    @PostMapping(value = "sys_statistics")
    @ApiOperation(value = "小程序维修端数据统计",position = 1)
    public ResponseData sys_statistics() {
        return ResponseData.success(wxSysCustomerInfoService.sys_statistics());
    }


    @WxUserCheck(userType = WxUserTypeEnum.SYS)
    @OpLog(title = "分页查询", opType = OpLogAnnotionOpTypeEnum.QUERY)
    @PostMapping(value = "page")
    @ApiOperation(value = "小程序维修端分页查询",position = 2)
    public ResponseData<PageWrapper<SysRepairInfoListRspVO>> page(@RequestBody SysRepairInfoPageQueryParam pageQueryParam) {
        return new SuccessResponseData<>(wxSysCustomerInfoService.pageVO(pageQueryParam));
    }

    @WxUserCheck(userType = WxUserTypeEnum.SYS)
    @OpLog(title = "详情", opType = OpLogAnnotionOpTypeEnum.DETAIL)
    @GetMapping(value = "{id}")
    @ApiOperation(value = "小程序维修端报修详情",position = 3)
    public ResponseData<RepairInfoVO> detail(@NotEmpty(message = "id不能为空，请检查参数id")  @PathVariable("id") String id) {
        return new SuccessResponseData<>(repairInfoService.getVO(id));
    }

    @WxUserCheck(userType = WxUserTypeEnum.SYS)
    @OpLog(title = "冻结", opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @PostMapping(value = "{id}")
    @ApiOperation(value = "小程序维修端报修冻结",position = 4)
    public ResponseData freeze(@NotEmpty(message = "id不能为空，请检查参数id")  @PathVariable("id") String id) {
        return ResponseData.bool(repairInfoService.freeze(id));
    }

    @WxUserCheck(userType = WxUserTypeEnum.SYS)
    @OpLog(title = "处理", opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @PostMapping(value = "edit")
    @ApiOperation(value = "小程序维修端处理",position = 5)
    public ResponseData deal(@Validated @RequestBody RepairInfoEditParam editParam) {
        return wxSysCustomerInfoService.deal(editParam);
    }

    @WxUserCheck(userType = WxUserTypeEnum.SYS)
    @OpLog(title = "test", opType = OpLogAnnotionOpTypeEnum.OTHER)
    @GetMapping(value = "test")
    public ResponseData test(){
        return ResponseData.success("这是sys用户登录访问");
    }

    @WxUserCheck(userType = WxUserTypeEnum.SYS,roles = {"GLRY"})
    @OpLog(title = "test1", opType = OpLogAnnotionOpTypeEnum.OTHER)
    @GetMapping(value = "test1")
    public ResponseData test1(){
        return ResponseData.success("这是sys用户GLRY角色登录访问");
    }


}
