package com.yunke.admin.modular.system.dict.model.param;

import com.yunke.admin.common.base.BaseEditParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class DictTypeEditParam extends BaseEditParam {

    /**
     * 字典id
     */
    @NotEmpty(message = "id不能为空")
    private String id;

    /**
     * 名称
     */
    @NotEmpty(message = "名称不能为空")
    private String name;

    /**
     * 编码
     */
    @NotEmpty(message = "编码不能为空")
    private String code;

    /**
     * 显示排序
     */
    @NotNull(message = "显示排序")
    private Integer sort;

    /**
     * 备注
     */
    private String remark;
}
