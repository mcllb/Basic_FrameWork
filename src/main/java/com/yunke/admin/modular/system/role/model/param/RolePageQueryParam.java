package com.yunke.admin.modular.system.role.model.param;

import com.yunke.admin.common.base.BasePageQueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class RolePageQueryParam extends BasePageQueryParam {

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 状态（字典：1正常 0停用 ）
     */
    private String status;
}
