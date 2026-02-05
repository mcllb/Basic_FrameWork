package com.yunke.admin.modular.system.issue.model.vo;

import com.yunke.admin.common.base.BaseVO;
import com.yunke.admin.framework.core.annotion.EnumDictField;
import com.yunke.admin.modular.system.issue.enums.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * @className IssueVO
 * @description: 缺陷管理_详情返回数据
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IssueVO extends BaseVO {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 缺陷来源
     */
    @EnumDictField(IssueFromEnum.class)
    private String issueFrom;

    private String issueFromText;
    /**
     * 标题
     */
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
    @EnumDictField(IssueTypeEnum.class)
    private String issueType;

    private String issueTypeText;
    /**
     * 缺陷等级
     */
    @EnumDictField(IssueLevelEnum.class)
    private String issueLevel;

    private String issueLevelText;
    /**
     * 优先级
     */
    @EnumDictField(IssuePriorityEnum.class)
    private String priorityLevel;

    private String priorityLevelText;
    /**
     * 缺陷描述
     */
    private String issueDescription;
    /**
     * 重现步骤
     */
    private String reproSteps;
    /**
     * 开启时间
     */
    private Date openTime;
    /**
     * 关闭时间
     */
    private Date closeTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态
     */
    @EnumDictField(IssueStatusEnum.class)
    private String status;

    private String statusText;
}