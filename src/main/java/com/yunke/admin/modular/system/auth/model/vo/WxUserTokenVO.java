package com.yunke.admin.modular.system.auth.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @className WxUserTokenVO
 * @description: 微信用户token模型
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/22
 */
@ApiModel("微信用户token模型")
@Data
public class WxUserTokenVO {

    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    private String userName;
    /**
     * 用户类型（sys系统用户 cust企业用户）
     */
    @ApiModelProperty(value = "用户类型（sys系统用户 cust企业用户）")
    private String userType;
    /**
     * token值
     */
    @ApiModelProperty(value = "token值")
    private String tokenValue;
    /**
     * token名称（其他接口请求header中的token名字）
     */
    @ApiModelProperty(value = "token名称（其他接口请求header中的token名字）")
    private String tokenName;

    /**
     * 微信openid
     */
    @ApiModelProperty(value = "微信openid")
    private Object loginId;
    /**
     * 登录类型（暂时无用）
     */
    @ApiModelProperty(value = "登录类型（暂时无用）")
    private String loginType;

    /**
     * 登录设备类型
     */
    @ApiModelProperty(value = "登录设备类型")
    private String loginDevice;

    /**
     * token标签（暂时无用）
     */
    @ApiModelProperty(value = "token标签（暂时无用）")
    private String tag;

    /**
     * 微信会话id
     */
    @ApiModelProperty(value = "微信会话id")
    private String sessionKey;

}
