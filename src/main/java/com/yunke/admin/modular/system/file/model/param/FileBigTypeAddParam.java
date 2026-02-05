package com.yunke.admin.modular.system.file.model.param;

import com.yunke.admin.common.base.BaseAddParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * @className FileBigTypeAddParam
 * @description: 系统附件类型表_新增请求参数
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FileBigTypeAddParam extends BaseAddParam {
    private static final long serialVersionUID = 1L;

    /**
     * 类型名称
     */
    @NotEmpty(message = "类型名称不能为空，请检查参数typeName")
    private String typeName;
    /**
     * 类型代码
     */
    @NotEmpty(message = "类型代码不能为空，请检查参数typeCode")
    private String typeCode;
    /**
     * 显示排序
     */
    private Integer sort;
    /**
     * 备注
     */
    private String remark;
}