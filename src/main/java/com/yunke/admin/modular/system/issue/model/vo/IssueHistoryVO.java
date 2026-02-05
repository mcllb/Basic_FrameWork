package com.yunke.admin.modular.system.issue.model.vo;

import com.yunke.admin.common.base.BaseVO;
import com.yunke.admin.framework.core.annotion.EnumDictField;
import com.yunke.admin.modular.system.issue.enums.IssueStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * @className IssueHistoryVO
 * @description: 缺陷管理历史_详情返回数据
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IssueHistoryVO extends BaseVO {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 缺陷主键
     */
    private String issueId;
    /**
     * 状态
     */
    @EnumDictField(IssueStatusEnum.class)
    private String status;

    private String statusText;
    /**
     * 处理意见
     */
    private String comment;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
}