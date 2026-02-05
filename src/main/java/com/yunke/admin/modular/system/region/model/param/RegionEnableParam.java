package com.yunke.admin.modular.system.region.model.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RegionEnableParam {

    @NotEmpty(message = "主键不能为空，请检查参数id")
    private String id;

    @NotEmpty(message = "状态不能为空，请检查参数enable")
    private String enable;

}
