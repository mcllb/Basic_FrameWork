package com.yunke.admin.modular.system.profile.model.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class UpdateUserInfoParam {

    /**
     * 用户id
     */
    @NotEmpty(message = "用户id不能为空")
    private String id;

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
     * 性别（字典：0未知 1男 2女）
     */
    private Integer sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机
     */
    @NotEmpty(message = "手机号码不能为空")
    private String phone;

    /**
     * 电话
     */
    private String telphone;

    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date birthday;

}
