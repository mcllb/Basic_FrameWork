package com.yunke.admin.modular.system.wx.model.param;

import com.yunke.admin.common.base.BasePageQueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.lang.String;


/**
 * @author tianlei
 * @version 1.0
 * @className WxUserPageQueryParam
 * @description: 微信用户表_分页查询请求参数
 * <p></p>
 * @date 2026/1/22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WxUserPageQueryParam extends BasePageQueryParam {
    /**
     * 唯一id（维修openid）
     */
    @NotEmpty(message = "主键不能为空，请检查参数id")
    private String id;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 用户类型（sys系统用户 cust企业用户）
     */
    private String userType;
    /**
     * 用户id（sys用户值为系统用户表id,cust时为客户表id）
     */
    private String userId;
    /**
     * 注册时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date registerTime;
}
