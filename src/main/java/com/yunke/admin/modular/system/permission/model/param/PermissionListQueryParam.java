package com.yunke.admin.modular.system.permission.model.param;

import com.yunke.admin.common.base.BaseQueryParam;
import com.yunke.admin.common.enums.CommonStatusEnum;
import com.yunke.admin.framework.core.validation.EnumDictValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PermissionListQueryParam extends BaseQueryParam {

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 状态
     */
    @EnumDictValidator(value = CommonStatusEnum.class,message = "状态值不在正确的枚举中")
    private String status;

}
