package com.yunke.admin.modular.system.wx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunke.admin.common.enums.CommonStatusEnum;
import com.yunke.admin.framework.mybatisplus.base.GeneralServiceImpl;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.modular.system.wx.mapper.WxUserMapper;
import com.yunke.admin.modular.system.wx.model.entity.WxUser;
import com.yunke.admin.modular.system.wx.model.param.WxUserAddParam;
import com.yunke.admin.modular.system.wx.model.param.WxUserEditParam;
import com.yunke.admin.modular.system.wx.model.param.WxUserPageQueryParam;
import com.yunke.admin.modular.system.wx.model.vo.WxUserVO;
import com.yunke.admin.modular.system.wx.service.WxUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.String;
import java.util.Date;
import java.util.List;

/**
 * @className WxUserServiceImpl
 * @description: 微信用户表_Service实现类
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/22
 */
@Service
public class WxUserServiceImpl extends GeneralServiceImpl<WxUserMapper, WxUser> implements WxUserService {

    @Override
    public PageWrapper<WxUserVO> pageVO(WxUserPageQueryParam pageQueryParam) {
        LambdaQueryWrapper<WxUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 查询条件：xxx
        
        // 查询条件：xxx
        
        // 排序
        lambdaQueryWrapper.orderByDesc(WxUser::getRegisterTime);

        Page<WxUser> pageData = this.page(pageQueryParam.page(), lambdaQueryWrapper);
        List<WxUser> records = pageData.getRecords();
        List<WxUserVO> voList = BeanUtil.copyListProperties(records, WxUserVO::new);
        PageWrapper<WxUserVO> pageWrapper = new PageWrapper<>(voList, pageData.getTotal(), pageData.getSize(), pageData.getCurrent());
        return pageWrapper;
    }
    
    @Override
    public WxUserVO getVO(String id){
        WxUserVO wxUserVO = null;
        WxUser wxUser = this.getById(id);
        if(wxUser != null){
        	wxUserVO = new WxUserVO();
            BeanUtil.copyProperties(wxUser,wxUserVO);
        }
        return wxUserVO;
    }

    @Transactional
    @Override
    public boolean add(WxUserAddParam addParam) {
        WxUser wxUser = new WxUser();
        BeanUtil.copyProperties(addParam,wxUser);
        wxUser.setRegisterTime(new Date());
        wxUser.setEnabled(CommonStatusEnum.ENABLE.getCode());
        return this.save(wxUser);
    }

    @Transactional
    @Override
    public boolean edit(WxUserEditParam editParam) {
        WxUser wxUser = new WxUser();
        BeanUtil.copyProperties(editParam,wxUser);
        wxUser.setRegisterTime(null);
        return this.updateById(wxUser);
    }

    @Transactional
    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }
}
