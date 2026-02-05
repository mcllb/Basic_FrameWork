package com.yunke.admin.modular.business.wx.cust.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.common.util.SaUtil;
import com.yunke.admin.common.util.StrUtil;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.modular.business.customer.model.entity.CustomerInfo;
import com.yunke.admin.modular.business.customer.service.CustomerInfoService;
import com.yunke.admin.modular.business.repair.model.entity.RepairInfo;
import com.yunke.admin.modular.business.repair.model.param.RepairInfoEditParam;
import com.yunke.admin.modular.business.wx.cust.mapper.WxCustCustomerInfoMapper;
import com.yunke.admin.modular.business.wx.cust.model.param.CustRepairInfoPageQueryParam;
import com.yunke.admin.modular.business.wx.cust.model.vo.CustRepairInfoListRspVO;
import com.yunke.admin.modular.business.wx.cust.model.vo.FindCustomerStatisticsInfoRspVO;
import com.yunke.admin.modular.business.wx.cust.service.WxCustCustomerInfoService;
import com.yunke.admin.modular.system.user.model.entity.User;
import com.yunke.admin.modular.system.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: MCLLB
 * @Date: 2026/1/27 16:14
 * @Version: v1.0.0
 * @Description:
 **/
@Service
public class WxCustCustomerInfoServiceImpl  extends ServiceImpl<WxCustCustomerInfoMapper, RepairInfo> implements WxCustCustomerInfoService {

    @Autowired
    private CustomerInfoService customerInfoService;
    @Autowired
    private UserService userService;

    @Override
    public PageWrapper<CustRepairInfoListRspVO> pageVO(CustRepairInfoPageQueryParam pageQueryParam) {
        String flag = pageQueryParam.getFlag();

        String keyword = pageQueryParam.getKeyword();

        String customerId = SaUtil.getDeptId();

        LambdaQueryWrapper<RepairInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        // 关键字查询
        lambdaQueryWrapper
                .like(StrUtil.isNotBlank(keyword),
                        RepairInfo::getRepairIssues, keyword);

        switch (flag) {
            case "1":    //全部不判断

                break;
            case "2":     //报修中
                lambdaQueryWrapper
                        .eq(StrUtil.isNotBlank(flag),
                                RepairInfo::getWorkOrderStatus, "1");
                break;
            case "3":     //已完成
                lambdaQueryWrapper
                        .in(StrUtil.isNotBlank(flag),
                                RepairInfo::getWorkOrderStatus, Arrays.asList("2","3","99"));
                break;
            default:
                break;
        }

        // 精确匹配条件
        lambdaQueryWrapper
                .eq(StrUtil.isNotBlank(customerId),
                        RepairInfo::getCustomerId, customerId);

        // 排序
        lambdaQueryWrapper.orderByDesc(RepairInfo::getReportTime);

        IPage<RepairInfo> pageData = this.page(pageQueryParam.page(), lambdaQueryWrapper);
        //PageWrapper pageWrapper3 = new PageWrapper<>(pageData);

        List<CustRepairInfoListRspVO> pageVOList = BeanUtil.copyListProperties(pageData.getRecords(), CustRepairInfoListRspVO::new);
        //填充字典 可选
        BeanUtil.fillListDataDictField(pageVOList);

        //去找部门下的第一个人
        CustomerInfo customerInfo = customerInfoService.getById(customerId);
        String repairTeam = customerInfo.getRepairTeam();
        User user = userService.lambdaQuery().eq(User::getDeptId, repairTeam).one();

        pageVOList.stream().forEach(s ->{
            if(s.getName() == null){
                s.setName(user==null?"待分配":user.getUserName());
            }
            if(s.getPhone() == null){
                s.setPhone(user==null?"待分配":user.getPhone());
            }
        });

        PageWrapper pageWrapper = new PageWrapper(pageVOList,pageData.getTotal(), pageData.getSize(), pageData.getCurrent());

        return pageWrapper;

    }

    @Override
    public boolean evaluate(RepairInfoEditParam editParam) {
        RepairInfo entity = this.getById(editParam.getId());
        if (entity == null) {
            return false;
        }
        BeanUtil.copyProperties(editParam, entity);
        entity.setReviewTime(LocalDateTime.now());
        entity.setWorkOrderStatus("3");
        return this.updateById(entity);
    }

    @Override
    public ResponseData cust_statistics() {

        String customerId = SaUtil.getDeptId();//获取登录人的id

        int allTotalCount = this.lambdaQuery()
                .eq(RepairInfo::getCustomerId, customerId)
                .count();

        int repairTotalCount = this.lambdaQuery()
                .eq(RepairInfo::getCustomerId, customerId)
                .eq(RepairInfo::getWorkOrderStatus, "1")
                .list().size();

        int completeTotalCount = this.lambdaQuery()
                .eq(RepairInfo::getCustomerId, customerId)
                .in(RepairInfo::getWorkOrderStatus, Arrays.asList("2", "3","99"))
                .list().size();


        FindCustomerStatisticsInfoRspVO vo = FindCustomerStatisticsInfoRspVO.builder()
                .allTotalCount(allTotalCount)
                .completeTotalCount(completeTotalCount)
                .repairTotalCount(repairTotalCount)
                .build();

        return ResponseData.success(vo);
    }
}
