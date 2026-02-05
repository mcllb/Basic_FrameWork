package com.yunke.admin.framework.openapi.validate;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;

@Data
public class OpenApiValidateParam {

    private HttpServletRequest request;

}
