package com.yunke.admin.modular.system.issue.model.param;

import com.yunke.admin.common.base.BasePageQueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @className IssuePageQueryParam
 * @description: 缺陷管理_分页查询请求参数
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IssuePageQueryParam extends BasePageQueryParam {
	private static final long serialVersionUID = 1L;

	/**
	 * 标题
	 */
	private String title;

	private String priorityLevel;

	private String issueFrom;
    
}
