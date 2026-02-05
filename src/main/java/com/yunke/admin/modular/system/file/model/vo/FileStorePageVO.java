package com.yunke.admin.modular.system.file.model.vo;

import com.yunke.admin.common.base.BaseVO;
import com.yunke.admin.framework.core.annotion.UserField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * @className FileStorePageVO
 * @description: 系统附件表_详情返回数据
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FileStorePageVO extends BaseVO {
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
     * 附件类型名称
     */
    private String bigTypeName;
    /**
     * 附件子类型编码
     */
    private String smallTypeCode;
    /**
     * 附件子类型名称
     */
    private String smallTypeName;
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
    @UserField
    private String createBy;
    private String createByText;
    /**
     * 创建时间
     */
    private Date createTime;
}