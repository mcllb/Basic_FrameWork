package com.yunke.admin.modular.business.wx.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.common.util.SaUtil;
import com.yunke.admin.common.util.StrUtil;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.mybatisplus.base.GeneralServiceImpl;
import com.yunke.admin.modular.business.customer.model.entity.CustomerInfo;
import com.yunke.admin.modular.business.repair.model.entity.RepairInfo;
import com.yunke.admin.modular.business.repair.model.param.RepairInfoEditParam;
import com.yunke.admin.modular.business.wx.sys.mapper.WxSysCustomerInfoMapper;
import com.yunke.admin.modular.business.wx.sys.model.param.SysRepairInfoPageQueryParam;
import com.yunke.admin.modular.business.wx.sys.model.vo.FindSysStatisticsInfoRspVO;
import com.yunke.admin.modular.business.wx.sys.model.vo.SysRepairInfoListRspVO;
import com.yunke.admin.modular.business.wx.sys.service.WxSysCustomerInfoService;
import com.yunke.admin.modular.system.file.model.param.FileUploadParam;
import com.yunke.admin.modular.system.file.model.vo.FileStorePageVO;
import com.yunke.admin.modular.system.file.service.FileUploadService;
import com.yunke.admin.modular.system.user.model.entity.User;
import com.yunke.admin.modular.system.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: MCLLB
 * @Date: 2026/1/28 14:46
 * @Version: v1.0.0
 * @Description:
 **/
@Service
public class WxSysCustomerInfoServiceImpl extends GeneralServiceImpl<WxSysCustomerInfoMapper, RepairInfo> implements WxSysCustomerInfoService {

    @Autowired
    private UserService userService;

    @Autowired
    private FileUploadService fileUploadService;

    @Override
    public ResponseData sys_statistics() {

        String repairTeamID = SaUtil.getDeptId();//获取维修人的部门id

        MPJLambdaWrapper<RepairInfo> wrapper = new MPJLambdaWrapper<>();

        // 选择字段
        wrapper.select(RepairInfo::getId);
               /* .select(CustomerInfo::getId);*/

        // 左连接Customer表
        wrapper.leftJoin(CustomerInfo.class, CustomerInfo::getId, RepairInfo::getCustomerId);

        wrapper.eq(CustomerInfo::getRepairTeam, repairTeamID);

        Integer allTotalCount = count(wrapper);
        //wrapper.clear();
        wrapper.eq(CustomerInfo::getRepairTeam, repairTeamID)
                .eq(RepairInfo::getWorkOrderStatus, "1");

        Integer repairTotalCount = count(wrapper);

        wrapper.eq(CustomerInfo::getRepairTeam, repairTeamID)
                .eq(RepairInfo::getWorkOrderStatus, Arrays.asList("2,3"));

        Integer completeTotalCount = count(wrapper);

        wrapper.eq(CustomerInfo::getRepairTeam, repairTeamID)
                .eq(RepairInfo::getWorkOrderStatus, Arrays.asList("99"));

        Integer freezeTotalCount = count(wrapper);

        FindSysStatisticsInfoRspVO vo = FindSysStatisticsInfoRspVO.builder()
                .allTotalCount(allTotalCount)
                .completeTotalCount(completeTotalCount)
                .repairTotalCount(repairTotalCount)
                .freezeTotalCount(freezeTotalCount)
                .build();

        return ResponseData.success(vo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData deal(RepairInfoEditParam editParam) {
        User currentUserInfo = userService.getById(SaUtil.getWxLoginUser().getUserId());

        RepairInfo entity = this.getById(editParam.getId());
        if (entity == null) {
            return ResponseData.fail("数据为空");
        }
        BeanUtil.copyProperties(editParam, entity);
        entity.setName(currentUserInfo.getUserName());
        entity.setPhone(currentUserInfo.getPhone());
        entity.setWorkOrderStatus("2");
        entity.setHandleTime(LocalDateTime.now());

        this.updateById(entity);

        /*fileUploadService.updateFileBusinessId(entity.getId(),editParam.getFileIds());*/

        return ResponseData.success();
    }

    @Override
    public PageWrapper<SysRepairInfoListRspVO> pageVO(SysRepairInfoPageQueryParam pageQueryParam) {
        String flag = pageQueryParam.getFlag();

        String keyword = pageQueryParam.getKeyword();

        String repairTeamID = SaUtil.getDeptId();//获取维修人的部门id

        MPJLambdaWrapper<RepairInfo> wrapper = new MPJLambdaWrapper<>();

        // 选择字段
        wrapper.selectAll(RepairInfo.class)
                .selectAs(CustomerInfo::getName, "customerName")  // 将 CustomerInfo.name 映射为 customerName
                .selectAs(CustomerInfo::getPhone, "customerPhone")
                .selectAs(CustomerInfo::getCompanyName, "companyName");;
        /* .select(CustomerInfo::getId);*/

        // 左连接Customer表
        wrapper.leftJoin(CustomerInfo.class, CustomerInfo::getId, RepairInfo::getCustomerId);

        wrapper.eq(CustomerInfo::getRepairTeam, repairTeamID);

        wrapper.like(StrUtil.isNotBlank(keyword),
                RepairInfo::getRepairIssues, keyword);

        switch (flag) {
            case "1":    //全部不判断

                break;
            case "2":     //待办
                wrapper
                        .eq(StrUtil.isNotBlank(flag),
                                RepairInfo::getWorkOrderStatus, "1");
                break;
            case "3":     //已办
                wrapper
                        .in(StrUtil.isNotBlank(flag),
                                RepairInfo::getWorkOrderStatus, Arrays.asList("2","3"));
                break;
            case "4":     //冻结
                wrapper
                        .in(StrUtil.isNotBlank(flag),
                                RepairInfo::getWorkOrderStatus, Arrays.asList("99"));
                break;
            default:
                break;
        }

        wrapper.orderByDesc(RepairInfo::getReportTime);

        IPage<SysRepairInfoListRspVO> pageData = this.baseMapper.selectJoinPage(pageQueryParam.page(), SysRepairInfoListRspVO.class, wrapper);
        List<SysRepairInfoListRspVO> records = pageData.getRecords();
        BeanUtil.fillListDataDictField(records);
        PageWrapper<SysRepairInfoListRspVO> pageWrapper = new PageWrapper<>(records, pageData.getTotal(), pageData.getSize(), pageData.getCurrent());
        return pageWrapper;
    }
}
