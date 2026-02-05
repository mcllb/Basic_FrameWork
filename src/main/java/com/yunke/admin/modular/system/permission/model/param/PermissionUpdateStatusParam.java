package com.yunke.admin.modular.system.permission.model.param;

import com.yunke.admin.common.enums.CommonStatusEnum;
import com.yunke.admin.framework.core.validation.EnumDictValidator;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PermissionUpdateStatusParam {

    /**
     * 权限id
     */
    @NotEmpty(message = "权限id不能为空")
    private String id;

    /**
     * 状态（字典：1正常 0停用 ）
     */
    @NotEmpty(message = "状态不能为空")
    @EnumDictValidator(value = CommonStatusEnum.class,message = "状态值不在正确的枚举中，请检查参数status")
    private String status;
}
