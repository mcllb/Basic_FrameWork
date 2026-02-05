package com.yunke.admin.common.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @className BaseController
 * @description: controller基类
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Slf4j
public class BaseController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpSession session;

    protected HttpServletRequest getRequest() {
        return this.request;
    }

    protected HttpServletResponse getResponse() {
        return this.response;
    }

    protected HttpSession getSession() {
        return this.session;
    }

}
