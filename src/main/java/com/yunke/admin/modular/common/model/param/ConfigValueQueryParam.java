package com.yunke.admin.modular.common.model.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ConfigValueQueryParam {

    /**
     * 配置key
     */
    @NotEmpty(message = "配置key不能为空")
    private String configKey;

}
