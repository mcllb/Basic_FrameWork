package com.yunke.admin.modular.system.wx.event;

import com.yunke.admin.modular.business.customer.model.entity.CustomerInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
  * @className UpdateCustomerInfoEvent
  * @description
  * <p></p>
  * @version 1.0
  * @author tianlei
  * @date 2026/1/28
  */
@Data
@AllArgsConstructor
public class UpdateCustomerInfoEvent {

    private CustomerInfo customerInfo;


}
