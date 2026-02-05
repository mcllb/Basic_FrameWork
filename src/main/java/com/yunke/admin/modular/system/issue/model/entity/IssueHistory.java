package com.yunke.admin.modular.system.issue.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunke.admin.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.lang.String;

/**
 * @className IssueHistory
 * @description: 缺陷管理历史_实体
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@TableName("sys_issue_history")
@EqualsAndHashCode(callSuper = true)
public class IssueHistory extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    @TableField(value = "id")
    private String id;
    /**
     * 缺陷主键
     */
    @TableField(value = "issue_id")
    private String issueId;
    /**
     * 状态
     */
    @TableField(value = "status")
    private String status;
    /**
     * 处理意见
     */
    @TableField(value = "comment")
    private String comment;
}
