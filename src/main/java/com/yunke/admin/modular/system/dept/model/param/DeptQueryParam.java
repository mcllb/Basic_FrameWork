package com.yunke.admin.modular.system.dept.model.param;

import com.yunke.admin.common.base.BaseQueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class DeptQueryParam extends BaseQueryParam {

    /**
     * 部门名称
     */
    private String deptName;
}
