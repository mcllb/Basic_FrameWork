package com.yunke.admin.modular.system.file.model.param;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @className FileUploadParam
 * @description: 上附件参数对象
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/26
 */
@Data
public class FileUploadParam {

    @NotNull(message = "上文件不能为空")
    private MultipartFile file;

    @NotEmpty(message = "附件类型编码不能为空，请检查参数bigTypeCode")
    private String bigTypeCode;

    @NotEmpty(message = "附件子类型编码不能为空，请检查参数smallTypeCode")
    private String smallTypeCode;

    @NotEmpty(message = "关联业务id不能为空，请检查参数businessId")
    private String businessId;

    public FileUploadParam() {

    }

    public FileUploadParam(MultipartFile file, String bigTypeCode, String smallTypeCode, String businessId) {
        this.file = file;
        this.bigTypeCode = bigTypeCode;
        this.smallTypeCode = smallTypeCode;
        this.businessId = businessId;
    }
}
