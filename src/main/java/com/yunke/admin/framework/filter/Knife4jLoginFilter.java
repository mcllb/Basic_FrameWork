package com.yunke.admin.framework.filter;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class Knife4jLoginFilter implements Filter {

    /***
     * basic auth验证
     */
    public static final String SwaggerBootstrapUiBasicAuthSession="SwaggerBootstrapUiBasicAuthSession";


    /**
     * 是否开启basic验证,默认不开启
     */
    private static boolean enableBasicAuth = false;

    private static String username = "admin";

    private static String password = "123321";



    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("启动 HTTP BasicAuth 后台管理");
        enableBasicAuth = Boolean.valueOf(filterConfig.getInitParameter("enableBasicAuth"));
        username = filterConfig.getInitParameter("username");
        password = filterConfig.getInitParameter("password");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(enableBasicAuth){
            //判断Session中是否存在
            Object swaggerSessionValue  =request.getSession().getAttribute(SwaggerBootstrapUiBasicAuthSession);
            if(swaggerSessionValue != null){
                filterChain.doFilter(request,response);
            }else{
                if (!checkAuth(request)) {
                    writeForbiddenCode(response);
                    return;
                } else {
                    request.getSession().setAttribute(SwaggerBootstrapUiBasicAuthSession,username);
                    filterChain.doFilter(request, response);
                }
            }
        }else{
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * 检查是否合法登录
     *
     * @param request 请求对象
     * @return 是否合法登录
     */
    private boolean checkAuth(HttpServletRequest request) {
        Console.log("Authorization===="+request.getHeader("Authorization"));
        return checkAuth(request.getHeader("Authorization"), username,password);
    }

    /**
     * 是否不合法的数组
     *
     * @param arr
     * @return 是否不合法的数组
     */
    private boolean isBadArray(String[] arr) {
        return arr == null || arr.length != 2;
    }

    /**
     * 检查是否合法登录
     *
     * @param authorization 认证后每次HTTP请求都会附带上 Authorization 头信息
     * @param username 用户名
     * @param password 密码
     * @return true = 认证成功/ false = 需要认证
     */
    private boolean checkAuth(String authorization, String username, String password) {
        if (StrUtil.isEmpty(authorization))
            return false;

        String[] basicArray = authorization.split("\\s+");
        if (isBadArray(basicArray))
            return false;

        String idpass =Base64.decodeStr(basicArray[1]);
        if (StrUtil.isEmpty(idpass))
            return false;

        String[] idpassArray = idpass.split(":");
        if (isBadArray(idpassArray))
            return false;

        return username.equalsIgnoreCase(idpassArray[0]) && password.equalsIgnoreCase(idpassArray[1]);
    }

    private void writeForbiddenCode(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setStatus(401);
        // 发送要求输入认证信息,则浏览器会弹出输入框
        httpServletResponse.setHeader("WWW-Authenticate","Basic realm=\"input Swagger Basic username & password \"");
        httpServletResponse.getWriter().write("You do not have permission to access this resource");
    }

}
