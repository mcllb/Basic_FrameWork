package com.yunke.admin.modular.system.permission.model.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PermissionUpdateSortParam {

    /**
     * 权限id
     */
    @NotEmpty(message = "权限id不能为空")
    private String id;

    /**
     * 显示排序
     */
    @NotNull(message = "显示排序不能为空，请检查参数sort")
    private Integer sort;
}
