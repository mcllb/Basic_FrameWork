package com.yunke.admin.modular.system.role.model.param;

import com.yunke.admin.common.base.BaseParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryUserGrantRoleListParam extends BaseParam {

    /**
     * 用户id
     */
    @NotEmpty(message = "用户id不能为空")
    private String userId;

}
