package com.yunke.admin.modular.system.dict.model.param;

import com.yunke.admin.common.base.BasePageQueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class DictTypePageQueryParam extends BasePageQueryParam {

    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

}
