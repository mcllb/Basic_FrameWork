package com.yunke.admin.framework.filter;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.yunke.admin.common.util.JSONUtil;
import com.yunke.admin.common.util.SqlUtil;
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

@Slf4j
public class SqlFilterRequestWrapper extends BaseRequestWrapper {

    /**
     * 保存处理后的参数
     */
    private final Map<String, String[]> params = new HashMap<String, String[]>();


    public SqlFilterRequestWrapper(HttpServletRequest request) {
        super(request);
        setParams(request);
    }
    private void setParams(HttpServletRequest request) {
        //将请求的的参数转换为map集合
        Map<String, String[]> parameterMap = request.getParameterMap();
        log.info("before replaceSqlKeyWords params={}",JSONUtil.toJsonStr(parameterMap));
        this.params.putAll(parameterMap);
        //去空操作
        this.modifyParameterValues();
        log.info("after replaceSqlKeyWords params={}",JSONUtil.toJsonStr(params));
    }
    /**
     * 将parameter的值去除空格后重写回去
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
                    tempValues[i] = SqlUtil.replaceKeyWords(values[i],true);
                }
                params.put(key, tempValues);
            }
        }
    }

    /**
     * 重写getParameterValues
     */
    @Override
    public String[] getParameterValues(String name) {
        return params.get(name);
    }

    /**
     * 重写getInputStream方法,处理body传参
     * 目前只处理json请求
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 非json类型，直接返回
        boolean isJsonReq = isJsonRequest();
        if (!isJsonReq) {
            return super.getInputStream();
        }

        //为空，直接返回
        String bodyStr = IOUtils.toString(super.getInputStream(), "utf-8");
        if (StrUtil.isEmpty(bodyStr)) {
            return super.getInputStream();
        }
        log.info("before replaceSqlKeyWords jsonParam={}",bodyStr);

        //json去除sql关键字
        if(JSONUtil.isTypeJSONObject(bodyStr)){
            JSONObject jsonObject = JSONUtil.parseObj(bodyStr);
            JSONUtil.replaceJSONObjectSqlKeyWords(jsonObject,true);
            String jsonStr = JSONUtil.toJsonStr(jsonObject);
            log.info("after replaceSqlKeyWords jsonParam={}",jsonStr);
            final ByteArrayInputStream bis = new ByteArrayInputStream(jsonStr.getBytes("utf-8"));
            return new MyServletInputStream(bis);
        }else if(JSONUtil.isTypeJSONArray(bodyStr)){
            JSONArray jsonArray = JSONUtil.parseArray(bodyStr);
            JSONUtil.replaceJSONOArraySqlKeyWords(jsonArray,true);
            String jsonStr = JSONUtil.toJsonStr(jsonArray);
            log.info("after replaceSqlKeyWords jsonParam={}",jsonStr);
            final ByteArrayInputStream bis = new ByteArrayInputStream(jsonStr.getBytes("utf-8"));
            return new MyServletInputStream(bis);
        }else{
            return super.getInputStream();
        }


    }
}
