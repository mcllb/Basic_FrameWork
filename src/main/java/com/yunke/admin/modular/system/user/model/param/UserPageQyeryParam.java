package com.yunke.admin.modular.system.user.model.param;

import com.yunke.admin.common.base.BasePageQueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserPageQyeryParam extends BasePageQueryParam {

    /**
     * 部门id
     */
    private String deptId;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 账号
     */
    private String account;

}
