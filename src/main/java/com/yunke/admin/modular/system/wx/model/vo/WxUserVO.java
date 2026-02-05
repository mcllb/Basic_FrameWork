package com.yunke.admin.modular.system.wx.model.vo;

import com.yunke.admin.common.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.lang.String;


/**
 * _详情返回数据
 *
 * @author tianlei 2026-01-22 09:26:31
 */
/**
 * @className WxUserVO
 * @description: 微信用户表_详情返回数据
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WxUserVO extends BaseVO {

    /**
     * 唯一id（维修openid）
     */
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
    private Date registerTime;
}