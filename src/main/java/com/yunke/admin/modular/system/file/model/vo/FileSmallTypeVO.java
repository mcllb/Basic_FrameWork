package com.yunke.admin.modular.system.file.model.vo;

import com.yunke.admin.common.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;


/**
 * @className FileSmallTypeVO
 * @description: 系统附件子类型表_详情返回数据
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FileSmallTypeVO extends BaseVO {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 大类型id
     */
    private String bigTypeId;
    /**
     * 大类型编码
     */
    private String bigTypeCode;
    /**
     * 类型名称
     */
    private String typeName;
    /**
     * 类型代码
     */
    private String typeCode;
    /**
     * 显示排序
     */
    private Integer sort;
    /**
     * 是否必须上传
     */
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
     * 允许上传文件类型（逗号分隔，*表示允许任意类型）
     */
    private String allowFileExtension;

    private List<String> allowFileExtensionList;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}