package com.yunke.admin.framework.filter;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CorsFilter implements Filter {

    private static final String OPTIONS_METHOD = "OPTIONS";

    private String tokenName;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String origin = req.getHeader(HttpHeaders.ORIGIN);
        if(log.isDebugEnabled()){
            log.debug("CorsFilter ====> origin:{}",origin);
        }
        if (!StrUtil.isEmpty(origin)) {
            res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
            String ACCESS_CONTROL_ALLOW_HEADERS = "Origin, x-requested-with, Content-Type, Accept, Authorization, #Access-Token#";
            ACCESS_CONTROL_ALLOW_HEADERS = ACCESS_CONTROL_ALLOW_HEADERS.replace("#Access-Token#", tokenName);
            res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, ACCESS_CONTROL_ALLOW_HEADERS);
            res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, OPTIONS, DELETE");
            res.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Cache-Control, Content-Language, Content-Type, Expires, Last-Modified, Pragma");

            if (OPTIONS_METHOD.equalsIgnoreCase(req.getMethod())) {
                res.setStatus(HttpServletResponse.SC_NO_CONTENT);
                res.setContentType(MediaType.TEXT_HTML_VALUE);
                res.setCharacterEncoding("utf-8");
                res.setContentLength(0);
                res.addHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "1800");
                return;
            }
        }
        filterChain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String tokenName = filterConfig.getInitParameter("tokenName");
        if(log.isDebugEnabled()){
            log.debug("CorsFilter ====> init tokenName={}",tokenName);
        }
        this.tokenName = tokenName;
    }
}
