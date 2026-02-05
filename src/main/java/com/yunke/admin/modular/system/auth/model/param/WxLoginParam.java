package com.yunke.admin.modular.system.auth.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
  * @className WxLoginParam
  * @description 微信登录请求参数
  * <p></p>
  * @version 1.0
  * @author tianlei
  * @date 2026/1/22
  */
@ApiModel("微信登录请求参数")
@Data
public class WxLoginParam {

    /**
     * 小程序id
     */
    @ApiModelProperty(value = "appId",required = true)
    @NotEmpty(message = "appId，请检查参数appId")
    private String appId;

    /**
     * wx.login返回code
     */
    @ApiModelProperty(value = "wx.login返回code",required = true)
    private String code;

}
