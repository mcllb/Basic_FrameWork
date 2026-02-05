package com.yunke.admin.modular.system.file.model.vo;

import com.yunke.admin.common.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * @className FileStoreVO
 * @description: 系统附件表_详情返回数据
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FileStoreVO extends BaseVO {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 业务id
     */
    private String businessId;
    /**
     * 附件类型编码
     */
    private String bigTypeCode;
    /**
     * 附件子类型编码
     */
    private String smallTypeCode;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 原始文件名称
     */
    private String originalFileName;
    /**
     * 文件扩展名
     */
    private String fileExtension;
    /**
     * 媒体类型
     */
    private String contentType;
    /**
     * 文件大小
     */
    private Long fileSize;
    /**
     * 绝对路径
     */
    private String absolutePath;
    /**
     * 相对路径（相对于系统设定的上传目录）
     */
    private String relativePath;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
}