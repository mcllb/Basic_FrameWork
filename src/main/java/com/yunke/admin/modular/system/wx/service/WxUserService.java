package com.yunke.admin.modular.system.wx.service;

import com.yunke.admin.common.enums.CommonStatusEnum;
import com.yunke.admin.framework.mybatisplus.base.GeneralService;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.modular.system.wx.model.entity.WxUser;
import com.yunke.admin.modular.system.wx.model.param.WxUserAddParam;
import com.yunke.admin.modular.system.wx.model.param.WxUserEditParam;
import com.yunke.admin.modular.system.wx.model.param.WxUserPageQueryParam;
import com.yunke.admin.modular.system.wx.model.vo.WxUserVO;
import java.lang.String;
import java.util.Date;

/**
 * @className WxUserService
 * @description: 微信用户表_Service接口
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/22
 */
public interface WxUserService extends GeneralService<WxUser> {
	
    /**
     * @description: 分页查询
     * <p></p>
     * @param pageQueryParam 分页查询请求参数
     * @return PageWrapper<WxUserVO>
     * @auth: tianlei
     * @date: 2026/1/22 09:36
     */
    PageWrapper<WxUserVO> pageVO(WxUserPageQueryParam pageQueryParam);
	
    /**
     * @description: 详情
     * <p></p>
     * @param id 分页查询
     * @return WxUserVO
     * @auth: tianlei
     * @date: 2026/1/22 09:36
     */
    WxUserVO getVO(String id);
    
    /**
     * @description: 新增
     * <p></p>
     * @param addParam 新增请求参数
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/22 09:36
     */
    boolean add(WxUserAddParam addParam);
	
    /**
     * @description: 编辑
     * <p></p>
     * @param editParam 编辑请求参数
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/22 09:37
     */
    boolean edit(WxUserEditParam editParam);
	
    /**
     * @description: 根据id删除
     * <p></p>
     * @param id 根据id删除
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/22 09:37
     */
    boolean delete(String id);

    /**
     * @description: 根据手机号删除
     * <p></p>
     * @param phone 手机号
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/22 09:42
     */
    default boolean deleteByPhone(String phone){
        return this.lambdaUpdate().eq(WxUser::getPhone,phone).remove();
    }

    /**
     * @description: 注册用户
     * <p></p>
     * @param wxUser
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/22 09:36
     */
    default boolean register(WxUser wxUser){
        wxUser.setRegisterTime(new Date());
        wxUser.setEnabled(CommonStatusEnum.ENABLE.getCode());
        return this.save(wxUser);
    }

    /**
     * @description: 获取已启用的用户
     * <p></p>
     * @param
     * @param id
     * @return WxUser
     * @auth: tianlei
     * @date: 2026/1/28 10:50
     */
    default WxUser getEnabledUserById(String id){
        return this.lambdaQuery().eq(WxUser::getId,id)
                .eq(WxUser::getEnabled,CommonStatusEnum.ENABLE.getCode())
                .one();
    }

}
