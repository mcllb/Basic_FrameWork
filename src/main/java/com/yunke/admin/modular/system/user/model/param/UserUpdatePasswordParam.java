package com.yunke.admin.modular.system.user.model.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserUpdatePasswordParam {

    /**
     * 用户id
     */
    //@NotEmpty(message = "用户id不能为空")
    private String id;

    /**
     * 原密码
     */
    @NotEmpty(message = "原密码不能为空")
    @Size(min = 6,max = 15,message = "密码长度为6到15位")
    private String oldPassword;

    /**
     * 新密码
     */
    @NotEmpty(message = "新密码不能为空")
    @Size(min = 6,max = 15,message = "密码长度为6到15位")
    private String newPassword;

}
