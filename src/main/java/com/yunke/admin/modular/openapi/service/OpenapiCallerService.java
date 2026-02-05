package com.yunke.admin.modular.openapi.service;

import com.yunke.admin.framework.mybatisplus.base.GeneralService;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.modular.openapi.model.entity.OpenapiCaller;
import com.yunke.admin.modular.openapi.model.param.OpenapiCallerAddParam;
import com.yunke.admin.modular.openapi.model.param.OpenapiCallerChangeStatusParam;
import com.yunke.admin.modular.openapi.model.param.OpenapiCallerEditParam;
import com.yunke.admin.modular.openapi.model.param.OpenapiCallerPageQueryParam;
import com.yunke.admin.modular.openapi.model.vo.OpenapiCallerVO;
import java.lang.String;

/**
 * @className OpenapiCallerService
 * @description: 开放接口调用方管理_Service接口
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
public interface OpenapiCallerService extends GeneralService<OpenapiCaller> {
	
   /**
    * @description: 分页查询
    * <p></p>
    * @param pageQueryParam 分页查询请求参数
    * @return PageWrapper<OpenapiCallerVO>
    * @auth: tianlei
    * @date: 2026/1/16 14:21
    */
    PageWrapper<OpenapiCallerVO> pageVO(OpenapiCallerPageQueryParam pageQueryParam);
	
   /**
    * @description: 详情
    * <p></p>
    * @param id 主键id
    * @return OpenapiCallerVO
    * @auth: tianlei
    * @date: 2026/1/16 14:21
    */
    OpenapiCallerVO getVO(String id);
    
    /**
     * @description: 新增
     * <p></p>
     * @param addParam 新增请求参数
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/16 14:21
     */
    boolean add(OpenapiCallerAddParam addParam);
	
    /**
     * @description: 编辑
     * <p></p>
     * @param editParam 编辑请求参数
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/16 14:22
     */
    boolean edit(OpenapiCallerEditParam editParam);
	
   /**
    * @description: 根据id删除
    * <p></p>
    * @param id 主键id
    * @return boolean
    * @auth: tianlei
    * @date: 2026/1/16 14:22
    */
    boolean delete(String id);

    /**
     * @description: 修改状态
     * <p></p>
     * @param changeStatusParam
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/16 14:22
     */
    boolean changeStatus(OpenapiCallerChangeStatusParam changeStatusParam);

}
