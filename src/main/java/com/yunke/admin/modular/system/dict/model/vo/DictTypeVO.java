package com.yunke.admin.modular.system.dict.model.vo;

import com.yunke.admin.common.base.BaseVO;
import com.yunke.admin.common.enums.CommonStatusEnum;
import com.yunke.admin.framework.core.annotion.EnumDictField;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class DictTypeVO extends BaseVO {

    /**
     * 字典id
     */
    private String id;

    /**
     * 名称
     */
    private String name;

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


}
