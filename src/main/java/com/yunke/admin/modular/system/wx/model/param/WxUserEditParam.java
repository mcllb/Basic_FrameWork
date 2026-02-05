package com.yunke.admin.modular.system.wx.model.param;

import com.yunke.admin.common.base.BaseEditParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.lang.String;

/**
 * @className WxUserEditParam
 * @description: 微信用户表_编辑请求参数
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WxUserEditParam extends BaseEditParam {

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
}