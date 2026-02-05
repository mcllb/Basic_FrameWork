package com.yunke.admin.modular.system.auth.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
  * @className WxRegisterParam
  * @description 微信用户注册请求参数
  * <p></p>
  * @version 1.0
  * @author tianlei
  * @date 2026/1/22
  */
@ApiModel("微信用户注册请求参数")
@Data
public class WxRegisterParam {

    /**
     * 小程序id
     */
    @ApiModelProperty(value = "appId",required = true)
    @NotEmpty(message = "appId，请检查参数appId")
    private String appId;

    /**
     * 微信用户openid
     */
    @ApiModelProperty(value = "微信用户openId",required = true)
    @NotEmpty(message = "微信用户openId不能为空，请检查参数openId")
    private String openId;

    /**
     * 手机号code
     */
    @ApiModelProperty(value = "手机号code",required = true)
    @NotEmpty(message = "手机号code不能为空，请检查参数code")
    private String code;

}
