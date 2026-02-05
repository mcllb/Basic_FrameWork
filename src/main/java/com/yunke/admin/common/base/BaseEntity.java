package com.yunke.admin.common.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @className BaseEntity
 * @description: 通用字段实体类,需要此通用字段的实体可继承此类
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -3510111504290964440L;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;

}
