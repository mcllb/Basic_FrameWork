package com.yunke.admin.modular.system.user.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunke.admin.common.base.BaseVO;
import com.yunke.admin.common.enums.CommonStatusEnum;
import com.yunke.admin.framework.core.annotion.EnumDictField;
import com.yunke.admin.modular.system.dept.enums.DeptRoleEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = true)
public class UserPageVO extends BaseVO {

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
     * 备注
     */
    private String remark;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 部门id
     */
    private String deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 部门角色
     */
    @EnumDictField(DeptRoleEnum.class)
    private String deptRole;
    private String deptRoleText;

    /**
     * 状态（字典：1正常 0停用 ）
     */
    private String status;

    /**
     *  是否锁定
     */
    private String locked;
    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date birthday;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}
