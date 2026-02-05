package com.yunke.admin.modular.system.shortcut.model.param;

import com.yunke.admin.common.enums.CommonStatusEnum;
import com.yunke.admin.framework.core.validation.EnumDictValidator;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ShortcutUpdateStatusParam {

    /**
     * 快捷方式id
     */
    @NotEmpty(message = "快捷方式id不能为空，请检查参数id")
    private String id;

    /**
     * 状态（字典：1正常 0停用 ）
     */
    @NotEmpty(message = "状态不能为空,请检查参数enable")
    @EnumDictValidator(value = CommonStatusEnum.class,message = "状态值不在正确的枚举中")
    private String enable;
}
