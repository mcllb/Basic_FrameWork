package com.yunke.admin.modular.monitor.log.service.impl;

import com.yunke.admin.framework.mybatisplus.base.GeneralServiceImpl;
import com.yunke.admin.modular.monitor.log.mapper.LogLoginMapper;
import com.yunke.admin.modular.monitor.log.model.entity.LogLogin;
import com.yunke.admin.modular.monitor.log.service.LogLoginService;
import org.springframework.stereotype.Service;

/**
 * @className LogLoginServiceImpl
 * @description: 系统访问日志表 服务实现类
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Service
public class LogLoginServiceImpl extends GeneralServiceImpl<LogLoginMapper, LogLogin> implements LogLoginService {

}
