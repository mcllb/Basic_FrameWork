package com.yunke.admin.modular.system.dict.model.vo;

import com.yunke.admin.common.base.BaseVO;
import com.yunke.admin.common.enums.CommonStatusEnum;
import com.yunke.admin.framework.core.annotion.EnumDictField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class DictDataPageVO extends BaseVO {

    /**
     * id
     */
    private String id;

    /**
     * 字典类型id
     */
    private String typeId;

    /**
     * 字典类型id
     */
    private String typeCode;

    /**
     * 值
     */
    private String value;

    /**
     * 编码
     */
    private String code;

    /**
     * 显示排序
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态（字典: 0正常 1停用）
     */
    @EnumDictField(CommonStatusEnum.class)
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
