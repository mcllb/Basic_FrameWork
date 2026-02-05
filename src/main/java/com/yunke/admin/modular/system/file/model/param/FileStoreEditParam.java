package com.yunke.admin.modular.system.file.model.param;

import com.yunke.admin.common.base.BaseEditParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @className FileStoreEditParam
 * @description: 系统附件表_编辑请求参数
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FileStoreEditParam extends BaseEditParam {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
}