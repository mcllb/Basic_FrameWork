package com.yunke.admin.framework.service;


import com.yunke.admin.framework.core.annotion.RepeatSubmit;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @className RepeatSubmitService
 * @description: 防重复提交接口
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
public interface RepeatSubmitService {


    boolean isRepeatSubmit(HttpServletRequest request, RepeatSubmit repeatSubmit, Method method, Object... args);

}
