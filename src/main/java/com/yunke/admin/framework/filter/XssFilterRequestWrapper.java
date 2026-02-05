package com.yunke.admin.framework.filter;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.yunke.admin.common.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * @className XssFilterRequestWrapper
 * @description: XSS、sql过滤处理
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Slf4j
public class XssFilterRequestWrapper extends BaseRequestWrapper {

    private static String[] SQL_KEYWORDS = {"master", "truncate", "insert", "select"
            , "delete", "update", "declare", "alter", "drop", "sleep"};
    //sql 替换字符
    private static String REPLACE_STR = "";

    /**
     * 保存处理后的参数
     */
    private final Map<String, String[]> params = new HashMap<String, String[]>();

    /**
     * @param request
     */
    public XssFilterRequestWrapper(HttpServletRequest request) {
        super(request);
        setParams(request);
    }

    private void setParams(HttpServletRequest request) {
        //将请求的的参数转换为map集合
        Map<String, String[]> parameterMap = request.getParameterMap();
        log.debug("before xss params={}", JSONUtil.toJsonStr(parameterMap));
        this.params.putAll(parameterMap);
        //去空操作
        this.modifyParameterValues();
        log.debug("after xss params={}",JSONUtil.toJsonStr(params));
    }

    /**
     * 将parameter的值去除xss sqlkeyword后重写回去
     */
    public void modifyParameterValues() {
        Set<String> set = params.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String key = it.next();
            String[] values = params.get(key);
            if(ArrayUtil.isNotEmpty(values)){
                int length = values.length;
                String[] tempValues = new String[length];
                for (int i = 0; i < length; i++) {
                    //防xss攻击
                    tempValues[i] = HtmlUtil.filter(values[i]);
                }
                params.put(key, tempValues);
            }
        }
    }

    @Override
    public String[] getParameterValues(String name) {
        return params.get(name);
    }



    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 非json类型，直接返回
        if (!isJsonRequest()) {
            return super.getInputStream();
        }

        //为空，直接返回
        String bodyStr = IOUtils.toString(super.getInputStream(), "utf-8");
        if (StrUtil.isEmpty(bodyStr)) {
            return super.getInputStream();
        }
        log.info("before xss jsonParam={}",bodyStr);

        //json xss 过滤
        if(JSONUtil.isTypeJSONObject(bodyStr)){
            JSONObject jsonObject = JSONUtil.xssFilter(JSONUtil.parseObj(bodyStr));
            String jsonStr = JSONUtil.toJsonStr(jsonObject);
            log.info("after xss jsonParam={}",jsonStr);
            final ByteArrayInputStream bis = new ByteArrayInputStream(jsonStr.getBytes("utf-8"));
            return new MyServletInputStream(bis);
        }else if(JSONUtil.isTypeJSONArray(bodyStr)){
            JSONArray jsonArray = JSONUtil.xssFilter(JSONUtil.parseArray(bodyStr));
            String jsonStr = JSONUtil.toJsonStr(jsonArray);
            log.info("after xss jsonParam={}",jsonStr);
            final ByteArrayInputStream bis = new ByteArrayInputStream(jsonStr.getBytes("utf-8"));
            return new MyServletInputStream(bis);
        }else{
            return super.getInputStream();
        }
    }
}
