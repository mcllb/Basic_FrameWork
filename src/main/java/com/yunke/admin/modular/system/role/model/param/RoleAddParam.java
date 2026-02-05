package com.yunke.admin.modular.system.role.model.param;

import com.yunke.admin.common.base.BaseAddParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoleAddParam extends BaseAddParam {


    /**
     * 角色名称
     */
    @NotEmpty(message = "角色名称不能为空")
    private String roleName;

    /**
     * 角色编码
     */
    @NotEmpty(message = "角色编码不能为空")
    private String roleCode;

    /**
     * 显示排序
     */
    @NotNull(message = "显示排序不能为空")
    private Integer sort;


    /**
     * 备注
     */
    private String remark;


}
