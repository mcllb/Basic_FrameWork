package com.yunke.admin.modular.system.config.model.vo;

import com.yunke.admin.common.base.BaseVO;
import com.yunke.admin.common.enums.CommonStatusEnum;
import com.yunke.admin.framework.core.annotion.EnumDictField;
import com.yunke.admin.modular.system.config.enums.ParamConfigTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = true)
public class ParamConfigPageVO extends BaseVO {

    /**
     * 参数id
     */
    private String id;

    /**
     * 参数名称
     */
    private String configName;

    /**
     * 参数键名
     */
    private String configKey;

    /**
     * 参数键值
     */
    private String configValue;

    /**
     * 配置类型（字典：SYSTEM系统配置 BUSINESS业务配置）
     */
    private String configType;

    /**
     * 数据类型（枚举字典：SYS_CONFIG_DATA_TYPE）
     */
    @EnumDictField(ParamConfigTypeEnum.class)
    private String dataType;
    private String dataTypeText;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态（字典: 1正常 0停用）
     */
    @EnumDictField(CommonStatusEnum.class)
    private String status;
    private String statusText;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
