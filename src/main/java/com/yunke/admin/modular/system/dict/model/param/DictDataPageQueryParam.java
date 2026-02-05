package com.yunke.admin.modular.system.dict.model.param;

import com.yunke.admin.common.base.BasePageQueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;


@Data
@EqualsAndHashCode(callSuper = true)
public class DictDataPageQueryParam extends BasePageQueryParam {

    /**
     * 字典类型id
     */
    @NotEmpty(message = "字典类型id不能为空")
    private String typeId;

    /**
     * 字典值
     */
    private String value;

    /**
     * 编码
     */
    private String code;

}
