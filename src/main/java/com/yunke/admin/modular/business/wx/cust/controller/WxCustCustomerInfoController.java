package com.yunke.admin.modular.business.wx.cust.controller;

import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.framework.core.annotion.OpLog;
import com.yunke.admin.framework.core.annotion.OpLogHeader;
import com.yunke.admin.framework.core.annotion.WxUserCheck;
import com.yunke.admin.framework.core.enums.OpLogAnnotionOpTypeEnum;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import com.yunke.admin.modular.business.repair.model.param.RepairInfoAddParam;
import com.yunke.admin.modular.business.repair.model.param.RepairInfoEditParam;
import com.yunke.admin.modular.business.repair.model.vo.RepairInfoVO;
import com.yunke.admin.modular.business.repair.service.RepairInfoService;
import com.yunke.admin.modular.business.wx.cust.model.param.UploadFileParam;
import com.yunke.admin.modular.business.wx.cust.model.param.CustRepairInfoPageQueryParam;
import com.yunke.admin.modular.business.wx.cust.model.vo.CustRepairInfoListRspVO;
import com.yunke.admin.modular.business.wx.cust.service.WxCustCustomerInfoService;
import com.yunke.admin.modular.system.file.model.param.FileUploadParam;
import com.yunke.admin.modular.system.file.service.FileUploadService;
import com.yunke.admin.modular.system.wx.enums.WxUserTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

/**
  * @className WxCustomerInfoController
  * @description 微信端客户信息_前端控制器
  * <p></p>
  * @version 1.0
  * @author mcllb
  * @date 2026/1/22
  */
@Slf4j
@OpLogHeader("微信端客户信息")
@RestController
@RequestMapping("/wx/cust/customer-info/")
@RequiredArgsConstructor
@Validated
@Api(tags = "微信客户端接口")
public class WxCustCustomerInfoController extends BaseController {

    private final WxCustCustomerInfoService wxCustCustomerInfoService;

    private final FileUploadService fileUploadService;

    private final RepairInfoService repairInfoService;


    @WxUserCheck(userType = WxUserTypeEnum.CUST)
    @OpLog(title = "新增", opType = OpLogAnnotionOpTypeEnum.INSERT)
    @PostMapping(value = "add")
    @ApiOperation(value = "小程序报修新增",position = 1)
    public ResponseData add(@Validated @RequestBody RepairInfoAddParam addParam) {
        return repairInfoService.add(addParam);
    }

    @WxUserCheck(userType = WxUserTypeEnum.CUST)
    @OpLog(title = "评价", opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @PostMapping(value = "evaluate")
    @ApiOperation(value = "小程序客户端评价",position = 3)
    public ResponseData evaluate(@Validated @RequestBody RepairInfoEditParam editParam) {
        return ResponseData.bool(wxCustCustomerInfoService.evaluate(editParam));
    }

    @WxUserCheck(userType = WxUserTypeEnum.CUST)
    @PostMapping(value = "cust_statistics")
    @ApiOperation(value = "小程序客户端数据统计",position = 4)
    public ResponseData cust_statistics() {
        return ResponseData.success(wxCustCustomerInfoService.cust_statistics());
    }

    @WxUserCheck(userType = WxUserTypeEnum.CUST)
    @OpLog(title = "分页查询", opType = OpLogAnnotionOpTypeEnum.QUERY)
    @PostMapping(value = "page")
    @ApiOperation(value = "小程序客户端分页查询",position = 5)
    public ResponseData<PageWrapper<CustRepairInfoListRspVO>> page(@RequestBody CustRepairInfoPageQueryParam pageQueryParam) {
        return new SuccessResponseData<>(wxCustCustomerInfoService.pageVO(pageQueryParam));
    }

    @WxUserCheck(userType = WxUserTypeEnum.CUST)
    @OpLog(title = "编辑", opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @PostMapping(value = "edit")
    @ApiOperation(value = "小程序报修编辑",position = 6)
    public ResponseData edit(@Validated @RequestBody RepairInfoEditParam editParam) {
        return repairInfoService.edit(editParam);
    }

    @WxUserCheck(userType = WxUserTypeEnum.CUST)
    @OpLog(title = "删除", opType = OpLogAnnotionOpTypeEnum.DELETE)
    @DeleteMapping(value = "{id}")
    @ApiOperation(value = "小程序报修删除",position = 7)
    public ResponseData delete(@NotEmpty(message = "id不能为空，请检查参数id")  @PathVariable("id") String id) {
        return ResponseData.bool(repairInfoService.delete(id));
    }

    /**
     * <p></p>
     * @return ResponseData
     * @auth: mcllb
     * @date: 2026/1/26 11:01
     */
    @WxUserCheck(userType = WxUserTypeEnum.CUST)
    @OpLog(title = "delFile", opType = OpLogAnnotionOpTypeEnum.OTHER)
    @DeleteMapping(value = "delFile")
    @ApiOperation(value = "小程序报修删除单业务下所有附件",position = 8)
    public ResponseData deleteFile(@NotEmpty(message = "关联业务主键不能为空，请检查参数businessId") String businessId,
                                   @NotEmpty(message = "附件类型编码不能为空，请检查参数bigTypeCode") String bigTypeCode) {
        //删除业务关联附件
        fileUploadService.deleteByBigType(bigTypeCode,businessId);

        return ResponseData.success();
    }

    @WxUserCheck(userType = WxUserTypeEnum.CUST)
    @OpLog(title = "详情", opType = OpLogAnnotionOpTypeEnum.DETAIL)
    @GetMapping(value = "{id}")
    @ApiOperation(value = "小程序报修详情",position = 8)
    public ResponseData<RepairInfoVO> detail(@NotEmpty(message = "id不能为空，请检查参数id")  @PathVariable("id") String id) {
        return new SuccessResponseData<>(repairInfoService.getVO(id));
    }

    @WxUserCheck(userType = WxUserTypeEnum.CUST)
    @OpLog(title = "test", opType = OpLogAnnotionOpTypeEnum.OTHER)
    @GetMapping(value = "test")
    public ResponseData test(){
        return ResponseData.success("这是cust用户登录访问");
    }

}
