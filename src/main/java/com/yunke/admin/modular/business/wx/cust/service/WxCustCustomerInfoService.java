package com.yunke.admin.modular.business.wx.cust.service;

import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.modular.business.repair.model.param.RepairInfoEditParam;
import com.yunke.admin.modular.business.wx.cust.model.param.CustRepairInfoPageQueryParam;
import com.yunke.admin.modular.business.wx.cust.model.vo.CustRepairInfoListRspVO;
import org.springframework.stereotype.Service;

/**
  * @className WxCustomerInfoService
  * @description 微信端客户信息_服务类
  * <p></p>
  * @version 1.0
  * @author tianlei
  * @date 2026/1/22
  */
@Service
public interface WxCustCustomerInfoService {


    boolean evaluate(RepairInfoEditParam editParam);

    ResponseData cust_statistics();

    PageWrapper<CustRepairInfoListRspVO> pageVO(CustRepairInfoPageQueryParam pageQueryParam);


}
