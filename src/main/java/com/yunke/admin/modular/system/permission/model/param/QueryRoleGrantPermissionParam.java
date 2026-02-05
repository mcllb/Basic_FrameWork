package com.yunke.admin.modular.system.permission.model.param;

import com.yunke.admin.common.base.BaseParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryRoleGrantPermissionParam extends BaseParam {

    /**
     * 角色id
     */
    @NotEmpty(message = "角色id不能为空")
    private String roleId;

}
