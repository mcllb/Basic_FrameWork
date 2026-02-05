package com.yunke.admin.modular.monitor.log.service.impl;

import com.yunke.admin.framework.mybatisplus.base.GeneralServiceImpl;
import com.yunke.admin.modular.monitor.log.mapper.LogOperMapper;
import com.yunke.admin.modular.monitor.log.model.entity.LogOper;
import com.yunke.admin.modular.monitor.log.service.LogOperService;
import org.springframework.stereotype.Service;

/**
 * @className LogOperServiceImpl
 * @description: 系统操作日志表 服务实现类
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Service
public class LogOperServiceImpl extends GeneralServiceImpl<LogOperMapper, LogOper> implements LogOperService {

}
