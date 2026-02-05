package com.yunke.admin.modular.system.file.model.param;

import com.yunke.admin.common.base.BaseEditParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * @className FileBigTypeEditParam
 * @description: 系统附件类型表_编辑请求参数
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FileBigTypeEditParam extends BaseEditParam {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @NotEmpty(message = "更新主键不能为空，请检查参数id")
    private String id;
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