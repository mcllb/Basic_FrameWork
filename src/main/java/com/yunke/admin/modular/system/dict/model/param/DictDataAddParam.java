package com.yunke.admin.modular.system.dict.model.param;

import com.yunke.admin.common.base.BaseAddParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class DictDataAddParam extends BaseAddParam {

    /**
     * 字典类型id
     */
    @NotEmpty(message = "字典类型id不能为空")
    private String typeId;

    /**
     * 字典类型编码
     */
    @NotEmpty(message = "字典类型编码不能为空")
    private String typeCode;

    /**
     * 值
     */
    @NotEmpty(message = "字典值不能为空")
    private String value;

    /**
     * 编码
     */
    @NotEmpty(message = "编码不能为空")
    private String code;

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
