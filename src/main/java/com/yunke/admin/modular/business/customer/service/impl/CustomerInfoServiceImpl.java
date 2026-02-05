package com.yunke.admin.modular.business.customer.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yunke.admin.common.model.CommonDictVO;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.common.util.SaUtil;
import com.yunke.admin.common.util.SpringUtil;
import com.yunke.admin.framework.core.util.SysConfigUtil;
import com.yunke.admin.modular.business.common.constant.BizConfigKeyConstant;
import com.yunke.admin.modular.business.customer.model.entity.CustomerInfo;
import com.yunke.admin.modular.business.customer.mapper.CustomerInfoMapper;
import com.yunke.admin.modular.business.customer.model.param.CustomerInfoAddParam;
import com.yunke.admin.modular.business.customer.model.param.CustomerInfoEditParam;
import com.yunke.admin.modular.business.customer.model.param.CustomerInfoPageQueryParam;
import com.yunke.admin.modular.business.customer.model.vo.CustomerInfoVO;
import com.yunke.admin.modular.business.customer.service.CustomerInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunke.admin.modular.system.dept.model.entity.Dept;
import com.yunke.admin.modular.system.dept.service.DeptService;
import com.yunke.admin.modular.system.wx.event.DeleteCustomerInfoEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author mcllb
 * @since 2026-01-19
 */
@Service
public class CustomerInfoServiceImpl extends ServiceImpl<CustomerInfoMapper, CustomerInfo> implements CustomerInfoService {
    @Autowired
    private DeptService deptService;

    @Override
    public PageWrapper<CustomerInfoVO> pageVO(CustomerInfoPageQueryParam pageQueryParam) {
        LambdaQueryWrapper<CustomerInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //数据权限 维修人员看自己部门的，管理人员看全部
        if(checkMaintainRole()){
            pageQueryParam.setRepairTeam(SaUtil.getDeptId());
        }

        // 第一组OR条件：公司相关查询
        lambdaQueryWrapper.and(StrUtil.isNotBlank(pageQueryParam.getCompany()), wrapper ->
                wrapper.like(CustomerInfo::getCompanyName, pageQueryParam.getCompany())
                        .or()
                        .like(CustomerInfo::getCompanyTel, pageQueryParam.getCompany())
        );

        // 第二组OR条件：维修人相关查询
        lambdaQueryWrapper.and(StrUtil.isNotBlank(pageQueryParam.getRepairMan()), wrapper ->
                wrapper.like(CustomerInfo::getName, pageQueryParam.getRepairMan())
                        .or()
                        .like(CustomerInfo::getPhone, pageQueryParam.getRepairMan())
        );

        // 精确匹配条件
        lambdaQueryWrapper
                .eq(StrUtil.isNotBlank(pageQueryParam.getArea()),
                        CustomerInfo::getArea, pageQueryParam.getArea())
                .eq(StrUtil.isNotBlank(pageQueryParam.getRepairTeam()),
                        CustomerInfo::getRepairTeam, pageQueryParam.getRepairTeam());

        // 排序
        lambdaQueryWrapper.orderByDesc(CustomerInfo::getId);

        IPage<CustomerInfo> pageData = this.page(pageQueryParam.page(), lambdaQueryWrapper);
        //PageWrapper pageWrapper3 = new PageWrapper<>(pageData);

        List<CustomerInfoVO> pageVOList = BeanUtil.copyListProperties(pageData.getRecords(), CustomerInfoVO::new);
        //填充字典 可选
        BeanUtil.fillListDataDictField(pageVOList);
        BeanUtil.fillListDeptField(pageVOList);
        //BeanUtil.fillListEnumDictField(pageVOList);
        PageWrapper pageWrapper = new PageWrapper(pageVOList,pageData.getTotal(), pageData.getSize(), pageData.getCurrent());

        return pageWrapper;
    }

    @Override
    public CustomerInfoVO getVO(String id) {
        CustomerInfoVO customerInfoVO = null;
        CustomerInfo customerInfo = this.getById(id);
        if(customerInfo != null){
            customerInfoVO = new CustomerInfoVO();
            BeanUtil.copyProperties(customerInfo,customerInfoVO);
        }
        return customerInfoVO;
    }

    @Override
    public boolean add(CustomerInfoAddParam addParam) {
        CustomerInfo customerInfo = new CustomerInfo();
        BeanUtil.copyProperties(addParam,customerInfo);
        //维修人员新增客户的时，维修组必须是自己部门的
        if(checkMaintainRole()){
            customerInfo.setRepairTeam(SaUtil.getDeptId());
        }
        return this.save(customerInfo);
    }

    @Override
    public boolean edit(CustomerInfoEditParam editParam) {
        CustomerInfo oldCustomerInfo = this.getById(editParam.getId());
        CustomerInfo customerInfo = new CustomerInfo();
        BeanUtil.copyProperties(editParam,customerInfo);
        //维修人员修改信息时不能修维修组
        if(checkMaintainRole()){
            customerInfo.setRepairTeam(null);
        }
        boolean ret = this.updateById(customerInfo);
        if(ret){
            String oldPhone = oldCustomerInfo.getPhone();
            if(StrUtil.isNotBlank(oldPhone) && !oldPhone.equals(editParam.getPhone())){
                SpringUtil.publishEventAsync(new DeleteCustomerInfoEvent(editParam.getId()));
            }
        }
        return ret;
    }

    @Override
    public boolean delete(String id) {
        //维修人员只能删除自己部门的
        if(checkMaintainRole()){
            return this.lambdaUpdate()
                    .eq(CustomerInfo::getId, id)
                    .eq(CustomerInfo::getRepairTeam, SaUtil.getDeptId())
                    .remove();
        }
        boolean ret = this.removeById(id);
        if(ret){
            SpringUtil.publishEventAsync(new DeleteCustomerInfoEvent(id));
        }
        return ret;
    }

    @Override
    public List<CommonDictVO> getDeptOptions() {
        String configValue = SysConfigUtil.getSysConfigValue(BizConfigKeyConstant.BIZ_WXBMMC, String.class);
        Dept parent = deptService.lambdaQuery().select(Dept::getId).eq(Dept::getDeptName, configValue).one();
        if(parent == null){
            return CollUtil.newArrayList();
        }
        List<Dept> deptList = deptService.lambdaQuery()
                .select(Dept::getId, Dept::getDeptName)
                .eq(Dept::getParentId, parent.getId())
                .orderByAsc(Dept::getSort)
                .list();
        if(CollUtil.isNotEmpty(deptList)){
            return deptList.stream().map(dept -> {
                return new CommonDictVO(dept.getDeptName(),dept.getId());
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
