package com.yunke.admin.modular.system.issue.model.param;

import com.yunke.admin.common.base.BaseEditParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @className IssueHistoryEditParam
 * @description: 缺陷管理历史_编辑请求参数
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IssueHistoryEditParam extends BaseEditParam {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
}