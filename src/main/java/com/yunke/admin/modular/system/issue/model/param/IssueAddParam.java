package com.yunke.admin.modular.system.issue.model.param;

import com.yunke.admin.common.base.BaseAddParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * @className IssueAddParam
 * @description: 缺陷管理_新增请求参数
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IssueAddParam extends BaseAddParam {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @NotEmpty(message = "主键不能为空，请检查参数id")
    private String id;
    /**
     * 缺陷来源
     */
    //@NotEmpty(message = "缺陷来源不能为空，请检查参数issueFrom")
    private String issueFrom;
    /**
     * 标题
     */
    @NotEmpty(message = "标题不能为空，请检查参数title")
    private String title;
    /**
     * 操作系统
     */
    private String os;
    /**
     * 浏览器
     */
    private String browser;
    /**
     * 缺陷类型
     */
    @NotEmpty(message = "缺陷类型不能为空，请检查参数issueType")
    private String issueType;
    /**
     * 缺陷等级
     */
    @NotEmpty(message = "缺陷等级不能为空，请检查参数issueLevel")
    private String issueLevel;
    /**
     * 优先级
     */
    @NotEmpty(message = "优先级不能为空，请检查参数priorityLevel")
    private String priorityLevel;
    /**
     * 缺陷描述
     */
    private String issueDescription;
    /**
     * 重现步骤
     */
    private String reproSteps;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态
     */
    private String status;
}