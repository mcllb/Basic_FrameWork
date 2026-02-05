package com.yunke.admin.modular.system.file.model.param;

import com.yunke.admin.common.base.BaseAddParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @className FileStoreAddParam
 * @description: 系统附件表_新增请求参数
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FileStoreAddParam extends BaseAddParam {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
}