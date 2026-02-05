package com.yunke.admin.modular.system.file.model.param;

import com.yunke.admin.common.base.BasePageQueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @className FileStorePageQueryParam
 * @description: 系统附件表_分页查询请求参数
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FileStorePageQueryParam extends BasePageQueryParam {
    private static final long serialVersionUID = 1L;

    /**
     * 附件类型编码
     */
    private String bigTypeCode;

    /**
     * 原始文件名称
     */
    private String originalFileName;

}
