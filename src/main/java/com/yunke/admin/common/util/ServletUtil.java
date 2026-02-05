package com.yunke.admin.common.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.URLUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedInputStream;
import java.io.File;
import java.nio.charset.Charset;

public class ServletUtil extends cn.hutool.extra.servlet.ServletUtil {

    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取response
     */
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    /**
     * 获取session
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取String参数
     */
    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    /**
     * 判断是否为ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        boolean isAjaxRequest = false;
        if (!StringUtils.isBlank(request.getHeader("x-requested-with"))
                && request.getHeader("x-requested-with").equals("XMLHttpRequest")) {
            isAjaxRequest = true;
        }
        return isAjaxRequest;
    }

    /**
     * 获取请求body
     * @param request
     * @param charset
     * @return
     */
    public static byte[] getBodyBytes(HttpServletRequest request,Charset charset){
        return getBody(request).getBytes(charset);
    }


    public static void writeFile(HttpServletResponse response,File file,String fileName){
        String contentType = (String) ObjectUtil.defaultIfNull(FileUtil.getMimeType(fileName), "application/octet-stream");
        BufferedInputStream in = null;
        try {
            in = FileUtil.getInputStream(file);
            String encodeText = URLUtil.encodeAll(fileName, CharsetUtil.charset("UTF-8"));
            response.setHeader("Content-Disposition", "attachment;filename=" + encodeText);
            response.setContentType(contentType);
            write(response, in);
        } finally {
            IoUtil.close(in);
        }
    }

    public static void writeFile(HttpServletResponse response,File file){
        String fileName = file.getName();
        writeFile(response,file,fileName);
    }



}
