package com.yunke.admin.modular.system.auth.model.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @className MockLoginParam
 * @description: 模拟登陆请求参数
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
public class MockLoginParam {

    @NotEmpty(message = "账号不能为空,请检查参数account")
    private String account;

    @NotEmpty(message = "token不能为空,请检查参数token")
    private String token;

}
