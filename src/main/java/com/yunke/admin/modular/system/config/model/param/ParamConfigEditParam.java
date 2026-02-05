package com.yunke.admin.modular.system.config.model.param;

import com.yunke.admin.common.base.BaseEditParam;
import com.yunke.admin.framework.core.validation.EnumDictValidator;
import com.yunke.admin.modular.system.config.enums.ParamConfigDataTypeEnum;
import com.yunke.admin.modular.system.config.enums.ParamConfigTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

@Data
@EqualsAndHashCode(callSuper = true)
public class ParamConfigEditParam extends BaseEditParam {

    /**
     * 参数id
     */
    @NotEmpty(message = "参数id不能为空")
    private String id;

    /**
     * 参数名称
     */
    @NotEmpty(message = "参数名称不能为空")
    private String configName;

    /**
     * 参数键名
     */
    @NotEmpty(message = "参数键名不能为空")
    private String configKey;

    /**
     * 参数键值
     */
    @NotEmpty(message = "参数键值不能为空")
    private String configValue;

    /**
     * 配置类型（枚举字典：SYS_CONFIG_TYPE）
     */
    @NotEmpty(message = "配置类型不能为空")
    @EnumDictValidator(value = ParamConfigTypeEnum.class,message = "配置类型不在正确的枚举中")
    private String configType;

    /**
     * 数据类型（枚举字典：SYS_CONFIG_DATA_TYPE）
     */
    @NotEmpty(message = "数据类型不能为空")
    @EnumDictValidator(value = ParamConfigDataTypeEnum.class,message = "数据类型不在正确的枚举中")
    private String dataType;

    /**
     * 备注
     */
    private String remark;
}
