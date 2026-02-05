package com.yunke.admin.modular.system.file.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunke.admin.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @className FileStore
 * @description: 系统附件表_实体
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@TableName("sys_file_store")
@EqualsAndHashCode(callSuper = true)
public class FileStore extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    @TableField(value = "id")
    private String id;
    /**
     * 业务id
     */
    @TableField(value = "business_id")
    private String businessId;
    /**
     * 附件类型编码
     */
    @TableField(value = "big_type_code")
    private String bigTypeCode;
    /**
     * 附件子类型编码
     */
    @TableField(value = "small_type_code")
    private String smallTypeCode;
    /**
     * 文件名称
     */
    @TableField(value = "file_name")
    private String fileName;
    /**
     * 原始文件名称
     */
    @TableField(value = "original_file_name")
    private String originalFileName;
    /**
     * 文件扩展名
     */
    @TableField(value = "file_extension")
    private String fileExtension;
    /**
     * 媒体类型
     */
    @TableField(value = "content_type")
    private String contentType;
    /**
     * 文件大小
     */
    @TableField(value = "file_size")
    private Long fileSize;
    /**
     * 绝对路径
     */
    @TableField(value = "absolute_path")
    private String absolutePath;
    /**
     * 相对路径（相对于系统设定的上传目录）
     */
    @TableField(value = "relative_path")
    private String relativePath;

}
