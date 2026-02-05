package com.yunke.admin.modular.system.dict.service;

import com.yunke.admin.common.model.SingleDeleteParam;
import com.yunke.admin.framework.mybatisplus.base.GeneralService;
import com.yunke.admin.modular.system.dict.model.entity.DictType;
import com.yunke.admin.modular.system.dict.model.param.DictTypeAddParam;
import com.yunke.admin.modular.system.dict.model.param.DictTypeEditParam;
import com.yunke.admin.modular.system.dict.model.param.DictTypeUpdateStatusParam;
import com.yunke.admin.modular.system.dict.model.param.DictUpdateSortParam;

/**
 * @className DictTypeService
 * @description: 系统字典类型表_服务接口
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
public interface DictTypeService extends GeneralService<DictType> {

    boolean add(DictTypeAddParam dictTypeAddParam);

    boolean edit(DictTypeEditParam dictTypeEditParam);

    boolean delete(SingleDeleteParam singleDeleteParam);

    boolean updateDictTypeStatus(DictTypeUpdateStatusParam dictTypeUpdateStatusParam);

    boolean updateSort(DictUpdateSortParam updateSortParam);

    void refreshCache(String id);
}
