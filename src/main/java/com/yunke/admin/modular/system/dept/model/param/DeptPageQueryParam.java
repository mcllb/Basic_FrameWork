package com.yunke.admin.modular.system.dept.model.param;

import com.yunke.admin.common.base.BasePageQueryParam;
import com.yunke.admin.modular.system.dept.constant.DeptConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeptPageQueryParam extends BasePageQueryParam {

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 部门id
     */
    private String deptId = DeptConstant.DEPT_ROOT_ID;

}
