package com.yunke.admin.modular.business.wx.cust.model.param;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: MCLLB
 * @Date: 2026/1/27 13:45
 * @Version: v1.0.0
 * @Description:
 **/
@Data
public class UploadFileParam {
    @NotEmpty(message = "业务主键不能为空，请检查参数businessId")
    private String businessId;

    MultipartFile[] files;

    @NotEmpty(message = "附件类型编码不能为空，请检查参数bigTypeCode")
    private String bigTypeCode;

    @NotEmpty(message = "附件子类型编码不能为空，请检查参数smallTypeCode")
    private String smallTypeCode;

}
