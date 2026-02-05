package com.yunke.admin.common.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SingleDeleteParam {

    /**
     * 被删除数据的id
     */
    @NotEmpty(message = "id不能为空")
    private String id;

}
