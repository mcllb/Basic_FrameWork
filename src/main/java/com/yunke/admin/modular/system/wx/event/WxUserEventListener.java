package com.yunke.admin.modular.system.wx.event;

import com.yunke.admin.common.enums.CommonStatusEnum;
import com.yunke.admin.modular.system.wx.enums.WxUserTypeEnum;
import com.yunke.admin.modular.system.wx.model.entity.WxUser;
import com.yunke.admin.modular.system.wx.service.WxUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
  * @className WxUserEventListener
  * @description TODO
  * <p></p>
  * @version 1.0
  * @author tianlei
  * @date 2026/1/28
  */
@Slf4j
@Component
public class WxUserEventListener {

    @Autowired
    private WxUserService wxUserService;

    @EventListener(DeleteCustomerInfoEvent.class)
    public void onDeleteCustomerInfo(@NotNull DeleteCustomerInfoEvent event) {
        WxUser wxUser = wxUserService.lambdaQuery()
                .eq(WxUser::getUserId, event.getId())
                .eq(WxUser::getUserType, WxUserTypeEnum.CUST.getCode())
                .one();
        if(wxUser != null){
            wxUserService.lambdaUpdate()
                    .set(WxUser::getEnabled, CommonStatusEnum.DISABLE.getCode())
                    .eq(WxUser::getId, wxUser.getId())
                    .update();
            log.info("禁用企业微信用户：id={},userId={}",wxUser.getId(),wxUser.getUserId());
        }
    }

    @EventListener(DeleteSysUserEvent.class)
    public void onDeleteSysUserEvent(@NotNull DeleteSysUserEvent event) {
        WxUser wxUser = wxUserService.lambdaQuery()
                .eq(WxUser::getUserId, event.getId())
                .eq(WxUser::getUserType, WxUserTypeEnum.SYS.getCode())
                .one();
        if(wxUser != null){
            wxUserService.lambdaUpdate()
                    .set(WxUser::getEnabled, CommonStatusEnum.DISABLE.getCode())
                    .eq(WxUser::getId, wxUser.getId())
                    .update();
            log.info("禁用系统微信用户：id={},userId={}",wxUser.getId(),wxUser.getUserId());
        }
    }

    @EventListener(DisableSysUserEvent.class)
    public void onDisableSysUserEvent(@NotNull DisableSysUserEvent event) {
        WxUser wxUser = wxUserService.lambdaQuery()
                .eq(WxUser::getUserId, event.getId())
                .eq(WxUser::getUserType, WxUserTypeEnum.SYS.getCode())
                .one();
        if(wxUser != null){
            wxUserService.lambdaUpdate()
                    .set(WxUser::getEnabled, CommonStatusEnum.DISABLE.getCode())
                    .eq(WxUser::getId, wxUser.getId())
                    .update();
            log.info("禁用系统微信用户：id={},userId={}",wxUser.getId(),wxUser.getUserId());
        }
    }

}
