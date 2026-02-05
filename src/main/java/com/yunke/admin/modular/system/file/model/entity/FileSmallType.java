package com.yunke.admin.modular.system.file.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunke.admin.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @className FileSmallType
 * @description: 系统附件子类型表_实体
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@TableName("sys_file_small_type")
@EqualsAndHashCode(callSuper = true)
public class FileSmallType extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    @TableField(value = "id")
    private String id;
    /**
     * 大类型id
     */
    @TableField(value = "big_type_id")
    private String bigTypeId;
    /**
     * 大类型代码
     */
    @TableField(value = "big_type_code")
    private String bigTypeCode;
    /**
     * 类型名称
     */
    @TableField(value = "type_name")
    private String typeName;
    /**
     * 类型代码
     */
    @TableField(value = "type_code")
    private String typeCode;
    /**
     * 显示排序
     */
    @TableField(value = "sort")
    private Integer sort;
    /**
     * 是否必须上传
     */
    @TableField(value = "is_must")
    private String isMust;
    /**
     * 允许上传文件大小（单位M）
     */
    @TableField(value = "allow_size")
    private Integer allowSize;
    /**
     * 允许上传文件数量（默认1）
     */
    @TableField(value = "allow_count")
    private Integer allowCount;
    /**
     * 允许上传文件类型（逗号分隔）
     */
    @TableField(value = "allow_file_extension")
    private String allowFileExtension;
    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;
}
