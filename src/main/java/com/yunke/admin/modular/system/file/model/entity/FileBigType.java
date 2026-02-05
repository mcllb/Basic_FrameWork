package com.yunke.admin.modular.system.file.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunke.admin.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统附件类型表
 *
 * @author tianlei 2021-04-12 22:04:29
 */
/**
 * @className FileBigType
 * @description: 系统附件类型表_实体
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@TableName("sys_file_big_type")
@EqualsAndHashCode(callSuper = true)
public class FileBigType extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    @TableField(value = "id")
    private String id;
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
     * 备注
     */
    @TableField(value = "remark")
    private String remark;
}
