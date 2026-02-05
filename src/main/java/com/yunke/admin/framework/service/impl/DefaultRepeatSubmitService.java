package com.yunke.admin.framework.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.yunke.admin.common.constant.CommonConstant;
import com.yunke.admin.common.util.DateUtil;
import com.yunke.admin.common.util.RedisUtil;
import com.yunke.admin.common.util.SaUtil;
import com.yunke.admin.framework.core.annotion.RepeatSubmit;
import com.yunke.admin.framework.core.exception.ServiceException;
import com.yunke.admin.framework.service.RepeatSubmitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @className DefaultRepeatSubmitService
 * @description: 默认防重复提交实现类
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Slf4j
public class DefaultRepeatSubmitService implements RepeatSubmitService {

    private final static String KEY_PREFIX = "REPEAT_SUBMIT" + CommonConstant.REDIS_KEY_SEPARATOR;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean isRepeatSubmit(HttpServletRequest request, RepeatSubmit repeatSubmit, Method method, Object... args) {
        log.debug("DefaultRepeatSubmitService ====  isRepeatSubmit");

        String token = SaUtil.getTokenValue();
        if(StrUtil.isEmpty(token)){
            throw new ServiceException("校验重复提交未获取到用户token");
        }

        // 获取类，方法
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();

        // 组装key：用户唯一标识+操作类+方法
        String key = token + "#" + className + "#" + methodName;
        String keyHashCode = String.valueOf(Math.abs(key.hashCode()));
        log.info("key:{},keyHashcode:{}", key, keyHashCode);

        key = KEY_PREFIX + key;

        // 从缓存给中根据key获取数据
        String value = (String)redisUtil.get(keyHashCode);
        if(value != null){
            log.info("重复提交,上次提交时间是：{}",value);
            return true;
        }

        //获取间隔时间
        int interval = repeatSubmit.interval();
        log.info("获取间隔时间{}", interval);

        String dateTime = DateUtil.format(DateUtil.date(), DatePattern.PURE_DATETIME_MS_FORMAT);
        redisUtil.set(keyHashCode, dateTime, interval);
        return false;
    }

    /**
     * @description: 生成方法标记：采用数字签名算法SHA1对方法签名字符串加签
     * <p></p>
     * @param
     * @param method
     * @param args
     * @return java.lang.String
     * @auth: tianlei
     * @date: 2026/1/14 15:19
     */
    private String getMethodSign(Method method, Object... args) {
        StringBuilder sb = new StringBuilder(method.toString());
        for (Object arg : args) {
            sb.append(toString(arg));
        }
        return DigestUtil.sha1Hex(sb.toString());
    }

    private String toString(Object arg) {
        if (Objects.isNull(arg)) {
            return "null";
        }
        if (arg instanceof Number) {
            return arg.toString();
        }
        return JSONUtil.toJsonStr(arg);
    }

}
