package com.yunke.admin.modular.system.user.model.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserResetPasswordParam {

    /**
     * 用户id
     */
    @NotEmpty(message = "用户id不能为空")
    private String id;

}
