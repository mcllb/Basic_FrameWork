package com.yunke.admin.framework.core.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.yunke.admin.common.constant.CommonConstant;
import com.yunke.admin.common.constant.SysConfigKeyConstant;
import com.yunke.admin.framework.cache.ParamConfigCache;
import com.yunke.admin.framework.config.ProjectConfig;
import com.yunke.admin.framework.core.exception.ServiceException;
import com.yunke.admin.modular.system.config.model.entity.ParamConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * @className SysConfigUtil
 * @description: 系统配置工具类
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Slf4j
public class SysConfigUtil {

    /**
     * @description: 获取原始项目配置属性,即在application.properties文件中定义的
     * @return com.yunke.admin.framework.config.ProjectConfig
     * @auth: tianlei
     * @date: 2026/1/14 14:33
     */
    public static ProjectConfig getOriginalProjectConfig() {
        ProjectConfig projectConfig = SpringUtil.getBean(ProjectConfig.class);
        return projectConfig;
    }

    /**
     * @description: 获取项目配置属性，部分属性用sys_config表中的值覆盖
     * <p>如无特殊情况，系统中获取项目配置属性皆使用此方法</p>
     * @return com.yunke.admin.framework.config.ProjectConfig
     * @auth: tianlei
     * @date: 2026/1/14 14:34
     */
    public static ProjectConfig getProjectConfig(){
        ProjectConfig projectConfig = getOriginalProjectConfig();
        projectConfig.setDefaultPassword(CommonConstant.DEFAULT_PASSWORD);
        String defaultPassword = getSysConfigValue(SysConfigKeyConstant.SYSTEM_DEFAULT_PASSWORD, String.class);
        if(StrUtil.isNotEmpty(defaultPassword)){
            projectConfig.setDefaultPassword(defaultPassword);
        }
        if(StrUtil.isEmpty(projectConfig.getDefaultPassword())){
            projectConfig.setDefaultPassword(CommonConstant.DEFAULT_PASSWORD);
        }

        // 覆盖name属性
        String name = getSysConfigValue(SysConfigKeyConstant.SYSTEM_NAME,String.class);
        if(StrUtil.isNotEmpty(name)){
            projectConfig.setName(name);
        }

        // 覆盖version
        String version = getSysConfigValue(SysConfigKeyConstant.SYSTEM_VERSION,String.class);
        if(StrUtil.isNotEmpty(version)){
            projectConfig.setVersion(version);
        }

        // 网站署名
        String copyRight = getSysConfigValue(SysConfigKeyConstant.SYSTEM_COPYRIGHT,String.class);
        if(StrUtil.isNotEmpty(copyRight)){
            projectConfig.setCopyRight(copyRight);
        }

        // 覆盖captchaType
        String captchaType = getSysConfigValue(SysConfigKeyConstant.SYSTEM_CAPTCHA_TYPE,String.class);
        if(StrUtil.isNotEmpty(captchaType)){
            projectConfig.setCaptchaType(captchaType);
        }

        // 覆盖captchaEnable
        String captchaEnable = getSysConfigValue(SysConfigKeyConstant.SYSTEM_CAPTCHA_ENABLE,String.class);
        if(StrUtil.isNotEmpty(captchaType)){
            projectConfig.setCaptchaEnable(Boolean.valueOf(captchaEnable));
        }
        return projectConfig;
    }

    /**
     * @description: 获取系统配置属性值，值为空或发生异常则返回null
     * @param configKey 配置key
     * @param configValueType 配置值类型
     * @return T
     * @auth: tianlei
     * @date: 2026/1/14 14:35
     */
    public static <T> T getSysConfigValue(String configKey,Class<T> configValueType){
        ParamConfig config = getSysConfigCache().get(configKey);
        if(config == null){
            return null;
        }
        String configValue = config.getConfigValue();
        if(StrUtil.isEmpty(configValue)){
            return null;
        }
        try{
            return Convert.convert(configValueType, configValue);
        }catch (Exception e){
            log.error(">>> 获取系统配置异常，configKey：{}，configValueType：{}，异常信息:{}",configKey,configValueType,e);
            return null;
        }

    }

    /**
     * @description: 获取系统配置属性值，值为空时返回默认值
     * @param configKey
     * @param configValueType
     * @param defaultValue
     * @return T
     * @auth: tianlei
     * @date: 2026/1/14 14:45
     */
    public static <T> T getSysConfigValue(String configKey,Class<T> configValueType,T defaultValue){
        T configValue = getSysConfigValue(configKey,configValueType);
        return configValue == null ? defaultValue : configValue;
    }

    public static Object getSysConfigValue(String configKey){
        ParamConfig config = getSysConfigCache().get(configKey);
        if(config == null){
            return null;
        }
        String dataType = config.getDataType();
        if(StrUtil.isEmpty(dataType)){
            log.error(">>> 获取系统配置异常，configKey：{}，异常信息:数据类型dataType为空",configKey);
            throw new ServiceException();
        }
        try {
            Class<?> clazz = Class.forName(dataType);
            return getSysConfigValue(configKey,clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            log.error(">>> 获取系统配置异常，configKey：{}，异常信息:{}",configKey,e);
            throw new ServiceException();
        }
    }

    public static ParamConfigCache getSysConfigCache(){
        return SpringUtil.getBean(ParamConfigCache.class);
    }


}
