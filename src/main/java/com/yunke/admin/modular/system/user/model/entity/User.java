package com.yunke.admin.modular.system.user.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunke.admin.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @className User
 * @description: 系统用户表_实体
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_user")
public class User extends BaseEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 加密密码的盐
     */
    private String salt;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户简称
     */
    private String shortName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别(字典: 0未知 1男 2女)
     */
    private String sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机
     */
    private String phone;

    /**
     * 电话
     */
    private String telphone;

    /**
     * 备注
     */
    private String remark;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 部门
     */
    private String deptId;

    /**
     * 部门角色
     */
    private String deptRole;

    /**
     * 最后登陆IP
     */
    private String lastLoginIp;

    /**
     * 最后登陆时间
     */
    private Date lastLoginTime;

    /**
     * 状态（字典：1正常 0停用 ）
     */
    private String status;

    /**
     *  是否锁定 (字典: 1是 0否 )
     */
    private String locked;

    /**
     * 是否启用模拟登陆（字典：1启用 0禁用 ）
     */
    @TableField(value = "enable_mock_login")
    private String enableMockLogin;
    /**
     * 生日
     */
    @TableField(value = "birthday")
    private Date birthday;

}
