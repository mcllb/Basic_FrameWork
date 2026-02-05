package com.yunke.admin.modular.system.file.model.param;

import com.yunke.admin.common.base.BaseEditParam;
import com.yunke.admin.common.enums.DataDictTypeEnum;
import com.yunke.admin.framework.core.validation.DataDictValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @className FileSmallTypeEditParam
 * @description: 系统附件子类型表_编辑请求参数
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FileSmallTypeEditParam extends BaseEditParam {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @NotEmpty(message = "更新主键不能为空，请检查参数id")
    private String id;
    /**
     * 大类型id
     */
    @NotEmpty(message = "大类型id不能为空，请检查参数bigTypeId")
    private String bigTypeId;
    /**
     * 大类型编码
     */
//    @NotEmpty(message = "大类型编码不能为空，请检查参数bigTypeCode")
//    private String bigTypeCode;
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
     * 是否必须上传
     */
    @NotEmpty(message = "是否必须上传不能为空，请检查参数isMust")
    private String isMust;
    /**
     * 允许上传文件大小（单位M）
     */
    private Integer allowSize;
    /**
     * 允许上传文件数量（默认1）
     */
    private Integer allowCount;
    /**
     * 允许上传文件类型（逗号分隔）
     */
    @NotNull(message = "允许上传文件类型不能为空，请检查参数allowFileExtension")
    @Size(min = 1,message = "允许上传文件类型不能为空，请检查参数allowFileExtension")
    @DataDictValidator(value = DataDictTypeEnum.FILE_EXPANDED_NAME,multiple = true)
    private List<String> allowFileExtensionList;
    /**
     * 备注
     */
    private String remark;
}