package com.yunke.admin.modular.openapi.model.vo;

import com.yunke.admin.common.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @className OpenapiCallerVO
 * @description: 开放接口调用方管理_详情返回数据
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OpenapiCallerVO extends BaseVO {

    /**
     * 主键
     */
    private String id;
    /**
     * 调用者名称
     */
    private String callerName;
    /**
     * 调用者标识
     */
    private String callerKey;
    /**
     * 安全密钥
     */
    private String secretKey;
    /**
     * 备注
     */
    private String reamrk;
    /**
     * 状态（1正常0停用）
     */
    private String status;
}