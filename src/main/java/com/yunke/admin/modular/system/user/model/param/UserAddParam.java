package com.yunke.admin.modular.system.user.model.param;

import cn.hutool.core.lang.Validator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunke.admin.common.base.BaseAddParam;
import com.yunke.admin.framework.core.validation.EnumDictValidator;
import com.yunke.admin.modular.system.user.enums.UserMockLoginEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserAddParam extends BaseAddParam {

    /**
     * 账号
     */
    @NotEmpty(message = "账号不能为空")
    private String account;


    /**
     * 用户姓名
     */
    @NotEmpty(message = "用户姓名不能为空")
    private String userName;

    /**
     * 用户简称
     */
    private String shortName;


    /**
     * 性别（字典:  0未知 1男 2女）
     */
    private String sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机
     */
    //@NotEmpty(message = "手机号码不能为空")
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
     * 显示排序
     */
    @NotNull
    private Integer sort;

    /**
     * 部门
     */
    @NotEmpty(message = "所属部门不能为空")
    private String deptId;

    /**
     * 部门角色
     */
    private String deptRole;

    /**
     * 是否启用模拟登陆（字典：1启用 0禁用 ）
     */
    @EnumDictValidator(value = UserMockLoginEnum.class,message = "模拟登陆不在正确的枚举中，请检查参数enableMockLogin")
    private String enableMockLogin;
    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date birthday;

    public static void main(String[] args) {
        boolean isEmail = Validator.isEmail("loolly@gmail.com");
    }

}
