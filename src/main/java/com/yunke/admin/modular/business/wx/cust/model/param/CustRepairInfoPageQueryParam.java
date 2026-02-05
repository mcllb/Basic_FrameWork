package com.yunke.admin.modular.business.wx.cust.model.param;

import com.yunke.admin.common.base.BasePageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: MCLLB
 * @Date: 2026/1/27 16:00
 * @Version: v1.0.0
 * @Description:
 **/
@Data
@ApiModel(value = "CustRepairInfoPageQueryParam", description = "CustRepairInfoPageQueryParam")
public class CustRepairInfoPageQueryParam extends BasePageQueryParam {

    @ApiModelProperty(value = "标识 1 全部  2 报修中  3 已完成")
    private String flag;

    @ApiModelProperty(value = "关键词")
    private String keyword;
}
