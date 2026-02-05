package com.yunke.admin.modular.system.config.service;

import com.yunke.admin.common.model.SingleDeleteParam;
import com.yunke.admin.framework.mybatisplus.base.GeneralService;
import com.yunke.admin.modular.system.config.model.entity.ParamConfig;
import com.yunke.admin.modular.system.config.model.param.ParamConfigAddParam;
import com.yunke.admin.modular.system.config.model.param.ParamConfigEditParam;
import com.yunke.admin.modular.system.config.model.param.ParamConfigUpdateStatusParam;

/**
 * <p>
 * 参数配置表 服务类
 * </p>
 *
 * @author tianlei
 * @since 2021-01-06
 */
public interface ParamConfigService extends GeneralService<ParamConfig> {

    boolean add(ParamConfigAddParam configAddParam);

    boolean edit(ParamConfigEditParam configEditParam);

    boolean delete(SingleDeleteParam singleDeleteParam);

    boolean updateConfigStatus(ParamConfigUpdateStatusParam configUpdateStatusParam);


}
