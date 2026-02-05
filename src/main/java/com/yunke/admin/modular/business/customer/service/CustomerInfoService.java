package com.yunke.admin.modular.business.customer.service;

import com.yunke.admin.common.model.CommonDictVO;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.common.util.SaUtil;
import com.yunke.admin.modular.business.common.constant.BizConfigKeyConstant;
import com.yunke.admin.modular.business.customer.model.entity.CustomerInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yunke.admin.modular.business.customer.model.param.CustomerInfoAddParam;
import com.yunke.admin.modular.business.customer.model.param.CustomerInfoEditParam;
import com.yunke.admin.modular.business.customer.model.param.CustomerInfoPageQueryParam;
import com.yunke.admin.modular.business.customer.model.vo.CustomerInfoVO;

import java.util.List;


/**
 *
 * @author mcllb
 * @since 2026-01-19
 */
public interface CustomerInfoService extends IService<CustomerInfo> {
    /**
     * @description: 分页查询
     * <p></p>
     * @param pageQueryParam 分页查询请求参数
     * @return PageWrapper<CustomerInfo>
     * @auth: mcllb
     * @date: 2026/1/16 14:15
     */
    PageWrapper<CustomerInfoVO> pageVO(CustomerInfoPageQueryParam pageQueryParam);

    /**
     * @description: 获取详情
     * <p></p>
     * @param id 主键id
     * @return CustomerInfo
     * @auth: mcllb
     * @date: 2026/1/16 14:17
     */
    CustomerInfoVO getVO(String id);

    /**
     * @description: 新增
     * <p></p>
     * @param addParam
     * @return boolean
     * @auth: mcllb
     * @date: 2026/1/16 14:17
     */
    boolean add(CustomerInfoAddParam addParam);

    /**
     * @description: 编辑
     * <p></p>
     * @param editParam
     * @return boolean
     * @auth: mcllb
     * @date: 2026/1/16 14:18
     */
    boolean edit(CustomerInfoEditParam editParam);

    /**
     * @description: 根据id删除
     * <p></p>
     * @param id 主键id
     * @return boolean
     * @auth: mcllb
     * @date: 2026/1/16 14:18
     */
    boolean delete(String id);

    /**
     * @className CustomerInfoService
     * @description: 获取维修组部门选项
     * <p></p>
     * @version 1.0
     * @author tianlei
     * @date 2026/1/21
     */
    List<CommonDictVO> getDeptOptions();

    /**
     * @description: 判断当前登录用户是否维修人员
     * <p></p> 
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/21 17:46
     */
    default boolean checkMaintainRole(){
        return SaUtil.checkRole(BizConfigKeyConstant.BIZ_WXRYJS);
    }

    /**
     * @description: 判断当前登录用户是否管理人员
     * <p></p>
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/21 17:47
     */
    default boolean checkManagerRole(){
        return SaUtil.checkRole(BizConfigKeyConstant.BIZ_GLRYJS);
    }
}
