package com.yunke.admin.modular.system.wx.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;
import java.lang.String;

/**
 * @className WxUser
 * @description: 微信用户表_实体
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/22
 */
@Data
@TableName("sys_wx_user")
public class WxUser {

    /**
     * 唯一id（维修openid）
     */
    @TableId(type = IdType.INPUT)
    @TableField(value = "id")
    private String id;
    /**
     * 手机号
     */
    @TableField(value = "phone")
    private String phone;
    /**
     * 用户类型（sys系统用户 cust企业用户）
     */
    @TableField(value = "user_type")
    private String userType;
    /**
     * 用户id（sys用户值为系统用户表id,cust时为客户表id）
     */
    @TableField(value = "user_id")
    private String userId;
    /**
     * 注册时间
     */
    @TableField(value = "register_time")
    private Date registerTime;
    /**
     * 是否启用（1启用 0禁用）
     */
    @TableField(value = "enabled")
    private String enabled;
}
