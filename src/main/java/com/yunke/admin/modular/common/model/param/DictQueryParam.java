package com.yunke.admin.modular.common.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @program: yunke-admin
 * @description:
 * @author: tianlei
 * @date: 2021-1-4
 */
@ApiModel("通用字典查询参数模型")
@Data
public class DictQueryParam {

    /**
     * 字典key
     */
    @ApiModelProperty(value = "字典key",required = true)
    @NotEmpty(message = "字典key不能为空")
    private String key;

}
