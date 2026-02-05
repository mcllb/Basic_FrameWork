package com.yunke.admin.modular.home.controller;

import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import com.yunke.admin.modular.home.model.param.UserLogLoginPageQueryParam;
import com.yunke.admin.modular.home.model.param.UserLogOperPageQueryParam;
import com.yunke.admin.modular.home.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className HomeController
 * @description: 系统首页接口
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@RestController
@RequestMapping("/home/")
@RequiredArgsConstructor
public class HomeController extends BaseController {

    private final HomeService homeService;

    /**
     * @description: 用户登录日志
     * <p></p>
     * @param pageQueryParam
     * @return com.yunke.admin.framework.core.response.ResponseData<com.yunke.admin.common.model.PageWrapper>
     * @auth: tianlei
     * @date: 2026/1/14 16:15
     */
    @PostMapping(value = "logLoginPage")
    public ResponseData<PageWrapper> logLoginPage(@RequestBody UserLogLoginPageQueryParam pageQueryParam){
        return new SuccessResponseData<>(homeService.logLoginPage(pageQueryParam));
    }

    /**
     * @description: 用户操作日志
     * <p></p>
     * @param pageQueryParam
     * @return com.yunke.admin.framework.core.response.ResponseData<com.yunke.admin.common.model.PageWrapper>
     * @auth: tianlei
     * @date: 2026/1/14 16:15
     */
    @PostMapping(value = "logOperPage")
    public ResponseData<PageWrapper> logOperPage(@RequestBody UserLogOperPageQueryParam pageQueryParam){
        return new SuccessResponseData<>(homeService.logOperPage(pageQueryParam));
    }
}
