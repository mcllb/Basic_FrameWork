package com.yunke.admin.modular.system.auth.model.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @className LoginParam
 * @description: 登陆请求参数
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
public class LoginParam {

    @NotEmpty(message = "账号不能为空")
    private String account;

    /**
     * 密码（base64编码）
     */
    @NotEmpty(message = "密码不能为空")
    private String password;

    //@NotEmpty(message = "验证码唯一标识不能为空")
    private String verKey;

    //@NotEmpty(message = "验证码不能为空")
    private String verCode;
}
