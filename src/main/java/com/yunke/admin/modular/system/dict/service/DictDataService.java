package com.yunke.admin.modular.system.dict.service;

import com.yunke.admin.common.model.SingleDeleteParam;
import com.yunke.admin.framework.mybatisplus.base.GeneralService;
import com.yunke.admin.modular.system.dict.model.entity.DictData;
import com.yunke.admin.modular.system.dict.model.param.DictDataAddParam;
import com.yunke.admin.modular.system.dict.model.param.DictDataEditParam;
import com.yunke.admin.modular.system.dict.model.param.DictDataUpdateStatusParam;
import com.yunke.admin.modular.system.dict.model.param.DictUpdateSortParam;

/**
 * @className DictDataService
 * @description: 系统字典值表_服务接口
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
public interface DictDataService extends GeneralService<DictData> {

    boolean add(DictDataAddParam dictDataAddParam);

    boolean edit(DictDataEditParam dictDataEditParam);

    boolean delete(SingleDeleteParam singleDeleteParam);

    boolean deleteByTypeId(String typeId);

    boolean updateDictDataStatus(DictDataUpdateStatusParam dictDataUpdateStatusParam);

    boolean updateSort(DictUpdateSortParam updateSortParam);

}
