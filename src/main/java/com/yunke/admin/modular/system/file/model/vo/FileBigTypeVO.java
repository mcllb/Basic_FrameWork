package com.yunke.admin.modular.system.file.model.vo;

import com.yunke.admin.common.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * @className FileBigTypeVO
 * @description: 系统附件类型表_详情返回数据
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FileBigTypeVO extends BaseVO {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
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