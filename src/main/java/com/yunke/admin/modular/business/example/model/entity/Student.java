package com.yunke.admin.modular.business.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunke.admin.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @className Student
 * @description: example演示
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ex_student")
public class Student extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 姓名
     */
    @TableField("age")
    private Integer age;

    /**
     * 生日
     */
    @TableField("birthday")
    private Date birthday;

    /**
     * 专业
     */
    @TableField("major")
    private String major;

    /**
     * 资产
     */
    @TableField("money")
    private BigDecimal money;

}
