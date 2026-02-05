package com.yunke.admin.modular.system.dept.model.param;

import com.yunke.admin.common.base.BaseEditParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeptEditParam extends BaseEditParam {

    /**
     * 部门id
     */
    @NotEmpty(message = "参数id不能为空")
    private String id;

    /**
     * 父级部门id
     */
    @NotEmpty(message = "上级部门部门不能为空")
    private String parentId;


    /**
     * 部门名称
     */
    @NotEmpty(message = "部门名称不能为空")
    private String deptName;

    /**
     * 部门编号
     */
    private String deptCode;

    /**
     * 部门领导
     */
    private String leader;

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
