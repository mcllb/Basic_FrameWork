package com.yunke.admin.modular.business.repair.service;

import com.yunke.admin.common.model.CommonDictVO;
import com.yunke.admin.common.model.PageWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.modular.business.repair.model.param.RepairInfoAddParam;
import com.yunke.admin.modular.business.repair.model.param.RepairInfoEditParam;
import com.yunke.admin.modular.business.repair.model.param.RepairInfoPageQueryParam;
import com.yunke.admin.modular.business.repair.model.vo.RepairInfoVO;
import com.yunke.admin.modular.business.repair.model.entity.RepairInfo;

import javax.validation.constraints.NotEmpty;
import java.util.List;


/**
 *
 * @author mcllb
 * @since 2026-01-22
 */
public interface RepairInfoService extends IService<RepairInfo> {
    /**
     * 分页查询
     *
     * @param pageQueryParam 分页查询请求参数
     * @return PageWrapper<RepairInfoVO>
     */
    PageWrapper<RepairInfoVO> pageVO(RepairInfoPageQueryParam pageQueryParam);

    /**
     * 获取详情
     *
     * @param id 主键id
     * @return RepairInfoVO
     */
    RepairInfoVO getVO(String id);

    /**
     * 新增
     *
     * @param addParam 新增参数
     * @return String
     */
    ResponseData add(RepairInfoAddParam addParam);

    /**
     * 编辑
     *
     * @param editParam 编辑参数
     * @return boolean
     */
    ResponseData edit(RepairInfoEditParam editParam);

    /**
     * 根据id删除
     *
     * @param id 主键id
     * @return boolean
     */
    boolean delete(String id);

    boolean freeze(@NotEmpty(message = "id不能为空，请检查参数id") String id);
}