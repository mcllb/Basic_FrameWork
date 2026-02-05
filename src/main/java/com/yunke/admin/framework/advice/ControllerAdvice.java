package com.yunke.admin.framework.advice;

import cn.hutool.core.collection.CollUtil;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.framework.core.response.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Collection;

@Slf4j
@RestControllerAdvice(basePackages = "com.yunke.admin")
public class ControllerAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if(body instanceof ResponseData){
            ResponseData responseData = (ResponseData)body;
            Object data = responseData.getData();
            if(data instanceof PageWrapper){
                parseBody(((PageWrapper) data).getRows());
            }else{
                parseBody(data);
            }

            return body;
        }else {
            return body;
        }
    }

    private void parseBody(Object body){
        if(body != null){
            if(BeanUtil.isBean(body.getClass())){
                writeObjField(body);
            }else if(body instanceof Collection){
                writeObjField((Collection)body);
            }
        }
    }

    private void writeObjField(Collection<?> beans){
        if(CollUtil.isNotEmpty(beans)){
            beans.forEach(item -> {
                writeObjField(item);
            });
        }
    }

    private void writeObjField(Object bean){
        if(bean != null && BeanUtil.isBean(bean.getClass())){
            BeanUtil.fill(bean);
        }
    }

}
