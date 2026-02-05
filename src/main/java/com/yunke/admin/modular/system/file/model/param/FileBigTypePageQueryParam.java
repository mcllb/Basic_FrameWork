package com.yunke.admin.modular.system.file.model.param;

import com.yunke.admin.common.base.BasePageQueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @className FileBigTypePageQueryParam
 * @description: 系统附件类型表_分页查询请求参数
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FileBigTypePageQueryParam extends BasePageQueryParam {
	private static final long serialVersionUID = 1L;

	/**
	 * 类型名称
	 */
	private String typeName;
	/**
	 * 类型代码
	 */
	private String typeCode;
    
}
