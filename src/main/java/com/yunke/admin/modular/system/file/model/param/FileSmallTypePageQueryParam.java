package com.yunke.admin.modular.system.file.model.param;

import com.yunke.admin.common.base.BasePageQueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;


/**
 * @className FileSmallTypePageQueryParam
 * @description: 系统附件子类型表_分页查询请求参数
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FileSmallTypePageQueryParam extends BasePageQueryParam {
	private static final long serialVersionUID = 1L;

	/**
	 * 大类型id
	 */
	@NotEmpty(message = "大类型id不能为空，请检查参数bigTypeId")
	private String bigTypeId;

	/**
	 * 类型名称
	 */
	private String typeName;
	/**
	 * 类型代码
	 */
	private String typeCode;
    
}
