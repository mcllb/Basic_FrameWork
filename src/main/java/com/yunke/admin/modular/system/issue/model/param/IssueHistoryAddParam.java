package com.yunke.admin.modular.system.issue.model.param;

import com.yunke.admin.common.base.BaseAddParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @className IssueHistoryAddParam
 * @description: 缺陷管理历史_新增请求参数
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IssueHistoryAddParam extends BaseAddParam {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
}