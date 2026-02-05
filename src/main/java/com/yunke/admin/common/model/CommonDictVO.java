package com.yunke.admin.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("通用字典模型")
@Data
public class CommonDictVO {

    /**
     * 字典内容
     */
    @ApiModelProperty(value = "字典内容")
    private String label;
    /**
     * 字典值
     */
    @ApiModelProperty(value = "字典值")
    private Object value;

    public CommonDictVO(String label,Object value){
        this.label = label;
        this.value = value;
    }

}
