package com.yunke.admin.modular.business.wx.sys.model.param;

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
@ApiModel(value = "SysRepairInfoPageQueryParam", description = "SysRepairInfoPageQueryParam")
public class SysRepairInfoPageQueryParam extends BasePageQueryParam {

    @ApiModelProperty(value = "标识 1 全部  2 待办  3 已办(已处理 已评价) 4 冻结")
    private String flag;

    @ApiModelProperty(value = "关键词")
    private String keyword;
}
