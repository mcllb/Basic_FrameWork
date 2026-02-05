package com.yunke.admin.modular.system.role.model.param;

import com.yunke.admin.common.base.BaseParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoleGrantPermissionParam extends BaseParam {

    /**
     * 角色Id
     */
    @NotEmpty(message = "角色Id不能为空")
    private String roleId;

    /**
     * 权限id集合
     */
    private List<String> permissionIdList;

}
