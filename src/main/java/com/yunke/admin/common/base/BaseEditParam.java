package com.yunke.admin.common.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @className BaseEditParam
 * @description:  CRUD 编辑参数封装基类
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseEditParam extends BaseParam {
    private static final long serialVersionUID = -7701570824628874200L;
}
