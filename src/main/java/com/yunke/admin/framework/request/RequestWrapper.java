package com.yunke.admin.framework.request;

import cn.hutool.core.util.CharsetUtil;
import com.yunke.admin.common.util.SaUtil;
import com.yunke.admin.common.util.ServletUtil;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.Map;

public class RequestWrapper extends HttpServletRequestWrapper{


    private final byte[] body;
    private final Map<String,String> headMap;
    private final Map<String,String> requestParamMap;
    private String token;

    public byte[] getBody() {
        return body;
    }

    public Map<String, String> getHeadMap() {
        return headMap;
    }

    public Map<String, String> getRequestParamMap() {
        return requestParamMap;
    }

    public String getToken(){
        return token;
    }

    public RequestWrapper(HttpServletRequest request){
        super(request);
        String atoken = ServletUtil.getHeader(request, SaUtil.getTokenName(), CharsetUtil.CHARSET_UTF_8);
        this.token = atoken;
        body = ServletUtil.getBodyBytes(request, CharsetUtil.CHARSET_UTF_8);
        headMap = ServletUtil.getHeaderMap(request);
        requestParamMap = ServletUtil.getParamMap(request);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        final ByteArrayInputStream bais = new ByteArrayInputStream(body);

        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };
    }

    @Override
    public String getHeader(String name) {
        return headMap.get(name.toLowerCase());
    }

    @Override
    public String getParameter(String name) {
        return requestParamMap.get(name);
    }


}
