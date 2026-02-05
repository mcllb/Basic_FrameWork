package com.yunke.admin.framework.filter;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.yunke.admin.common.util.SpringUtil;
import com.yunke.admin.framework.cache.ParamConfigCache;
import com.yunke.admin.modular.system.config.model.entity.ParamConfig;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @className XssFilter
 * @description: 防止XSS攻击的过滤器
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
public class XssFilter implements Filter {
    /**
     * 排除链接
     */
    public List<String> excludes = new ArrayList<>();


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String tempExcludes = filterConfig.getInitParameter("excludes");
        if (StrUtil.isNotEmpty(tempExcludes)) {
            String[] url = tempExcludes.split(",");
            for (int i = 0; url != null && i < url.length; i++) {
                excludes.add(url[i]);
            }
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (handleExcludeURL(request, response)) {
            filterChain.doFilter(request, response);
            return;
        }
        XssFilterRequestWrapper requestWrapper = new XssFilterRequestWrapper(request);
        filterChain.doFilter(requestWrapper, response);
    }

    private boolean handleExcludeURL(HttpServletRequest request, HttpServletResponse response) {
        List<String> excludeList = CollUtil.newArrayList();
        excludeList.addAll(excludes);
        ParamConfigCache paramConfigCache = SpringUtil.getBean(ParamConfigCache.class);
        ParamConfig paramConfig = paramConfigCache.get("xss.excludes");
        if(paramConfig != null){
            String configValue = paramConfig.getConfigValue();
            String[] split = configValue.split(",");
            CollUtil.addAll(excludeList,split);
        }
        if (CollUtil.isEmpty(excludeList)) {
            return false;
        }
        String url = request.getServletPath();
        for (String pattern : excludeList) {
            Pattern p = Pattern.compile("^" + pattern);
            Matcher m = p.matcher(url);
            if (m.find()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}
