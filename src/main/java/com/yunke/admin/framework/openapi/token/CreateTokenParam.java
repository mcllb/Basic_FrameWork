package com.yunke.admin.framework.openapi.token;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateTokenParam {
    /**
     * 调用者标识
     */
    @NotBlank(message = "调用者标识不能为空，请检查参数callerKey")
    private String callerKey;
    /**
     * 调用者安全密钥，需要MD5加密
     */
    @NotBlank(message = "安全密钥不能为空，请检查参数secretKey")
    private String secretKey;

}
