package com.yunke.admin.framework.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;

@Slf4j
public class BaseRequestWrapper extends HttpServletRequestWrapper {

    public BaseRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    /**
     * 是否是Json请求
     */
    public boolean isJsonRequest() {
        String contentType = super.getHeader(HttpHeaders.CONTENT_TYPE);
        boolean isJsonReq = MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(contentType) || MediaType.APPLICATION_JSON_UTF8_VALUE.equalsIgnoreCase(contentType);
        log.debug("isJsonReq={},contentType={},url={},uri={},servletPath={}",isJsonReq,contentType,super.getRequestURL(),super.getRequestURI(),super.getServletPath());
        return isJsonReq;
    }

    class MyServletInputStream extends ServletInputStream {
        private ByteArrayInputStream bis;
        public MyServletInputStream(ByteArrayInputStream bis){
            this.bis=bis;
        }
        @Override
        public boolean isFinished() {
            return true;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {

        }
        @Override
        public int read(){
            return bis.read();
        }
    }

}
