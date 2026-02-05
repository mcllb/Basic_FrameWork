package com.yunke.admin.modular.system.user.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunke.admin.common.base.BaseVO;
import com.yunke.admin.common.enums.CommonStatusEnum;
import com.yunke.admin.framework.core.annotion.DeptField;
import com.yunke.admin.framework.core.annotion.EnumDictField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserInfoVO extends BaseVO {

    /**
     * 用户id
     */
    private String id;

    /**
     * 账号
     */
    private String account;

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
     * 性别（字典：0未知 1男 2女）
     */
    @EnumDictField(CommonStatusEnum.class)
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
     * 部门id
     */
    @DeptField(valueFieldName = "deptName")
    private String deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 部门编号
     */
    private String deptCode;

    /**
     * 部门角色
     */
    private String deptRole;
    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date birthday;

    private List<String> roles;

    private List<String> permissions;

}
