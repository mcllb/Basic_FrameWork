package com.yunke.admin.modular.system.issue.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunke.admin.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import java.lang.String;

/**
 * @className Issue
 * @description: 缺陷管理_实体
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@TableName("sys_issue")
@EqualsAndHashCode(callSuper = true)
public class Issue extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    @TableField(value = "id")
    private String id;
    /**
     * 缺陷来源
     */
    @TableField(value = "issue_from")
    private String issueFrom;
    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;
    /**
     * 操作系统
     */
    @TableField(value = "os")
    private String os;
    /**
     * 浏览器
     */
    @TableField(value = "browser")
    private String browser;
    /**
     * 缺陷类型
     */
    @TableField(value = "issue_type")
    private String issueType;
    /**
     * 缺陷等级
     */
    @TableField(value = "issue_level")
    private String issueLevel;
    /**
     * 优先级
     */
    @TableField(value = "priority_level")
    private String priorityLevel;
    /**
     * 缺陷描述
     */
    @TableField(value = "issue_description")
    private String issueDescription;
    /**
     * 重现步骤
     */
    @TableField(value = "repro_steps")
    private String reproSteps;
    /**
     * 开启时间
     */
    @TableField(value = "open_time")
    private Date openTime;
    /**
     * 关闭时间
     */
    @TableField(value = "close_time")
    private Date closeTime;
    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;
    /**
     * 状态
     */
    @TableField(value = "status")
    private String status;
}
