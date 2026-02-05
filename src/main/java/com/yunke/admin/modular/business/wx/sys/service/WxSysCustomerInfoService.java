package com.yunke.admin.modular.business.wx.sys.service;

import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.modular.business.repair.model.param.RepairInfoEditParam;
import com.yunke.admin.modular.business.wx.sys.model.param.SysRepairInfoPageQueryParam;
import com.yunke.admin.modular.business.wx.sys.model.vo.SysRepairInfoListRspVO;

/**
  * @className WxCustomerInfoService
  * @description 微信端客户信息_服务类
  * <p></p>
  * @version 1.0
  * @author tianlei
  * @date 2026/1/22
  */
public interface WxSysCustomerInfoService {


    ResponseData sys_statistics();

    ResponseData deal(RepairInfoEditParam editParam);

    PageWrapper<SysRepairInfoListRspVO> pageVO(SysRepairInfoPageQueryParam pageQueryParam);
}
