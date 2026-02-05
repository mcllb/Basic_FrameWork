package com.yunke.admin.modular.common.controller;

import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.common.util.IdUtil;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/common/")
public class CommonController extends BaseController {

    @GetMapping(value = "generateId")
    public ResponseData<String> generateId(){
        return new SuccessResponseData<>(IdUtil.getSnowflakeNextIdStr());
    }


}
