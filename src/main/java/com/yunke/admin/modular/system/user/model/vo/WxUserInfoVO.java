package com.yunke.admin.modular.system.user.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunke.admin.common.base.BaseVO;
import com.yunke.admin.common.enums.CommonStatusEnum;
import com.yunke.admin.framework.core.annotion.DeptField;
import com.yunke.admin.framework.core.annotion.EnumDictField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * @className WxUserInfoVO
 * @description: 微信登录用户信息模型
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/26
 */
@ApiModel("微信登录用户信息模型")
@Data
@EqualsAndHashCode(callSuper = true)
public class WxUserInfoVO extends BaseVO {

    /**
     * 用户Id
     */
    @ApiModelProperty(value = "用户Id")
    private String id;
    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号")
    private String account;
    /**
     * 用户类型（sys系统用户 cust企业用户）
     */
    @ApiModelProperty(value = "用户类型（sys系统用户 cust企业用户）")
    private String userType;
    /**
     * 用户姓名
     */
    @ApiModelProperty(value = "用户姓名")
    private String userName;
    /**
     * 用户简称
     */
    @ApiModelProperty(value = "用户简称")
    private String shortName;
    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * 性别（字典：0未知 1男 2女）
     */
    @ApiModelProperty(value = "性别（字典：0未知 1男 2女）")
    @EnumDictField(CommonStatusEnum.class)
    private String sex;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 手机
     */
    @ApiModelProperty(value = "手机")
    private String phone;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String telphone;

    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    @DeptField(valueFieldName = "deptName")
    private String deptId;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    /**
     * 部门编号
     */
    @ApiModelProperty(value = "部门编号")
    private String deptCode;

    /**
     * 部门角色
     */
    @ApiModelProperty(value = "部门角色")
    private String deptRole;
    /**
     * 生日
     */
    @ApiModelProperty(value = "生日")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date birthday;
    /**
     * 用户角色
     */
    @ApiModelProperty(value = "用户角色")
    private List<String> roles;
    /**
     * 用户角色权限
     */
    @ApiModelProperty(value = "用户角色权限")
    private List<String> permissions;

}
