package com.yunke.admin.modular.system.issue.model.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class IssueReopenParam {

    /**
     * 缺陷主键
     */
    @NotEmpty(message = "缺陷主键不能为空，请检查参数id")
    private String id;

    /**
     * 处理意见
     */
    private String comment;
}
