package com.yunke.admin.modular.business.repair.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.common.util.SaUtil;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.modular.business.customer.model.entity.CustomerInfo;
import com.yunke.admin.modular.business.repair.mapper.RepairInfoMapper;
import com.yunke.admin.modular.business.repair.model.entity.RepairInfo;
import com.yunke.admin.modular.business.repair.model.param.RepairInfoAddParam;
import com.yunke.admin.modular.business.repair.model.param.RepairInfoEditParam;
import com.yunke.admin.modular.business.repair.model.param.RepairInfoPageQueryParam;
import com.yunke.admin.modular.business.repair.model.vo.RepairInfoVO;
import com.yunke.admin.modular.business.repair.service.RepairInfoService;
import com.yunke.admin.modular.system.file.model.param.FileUploadParam;
import com.yunke.admin.modular.system.file.model.vo.FileStoreVO;
import com.yunke.admin.modular.system.file.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author mcllb
 * @since 2026-01-22
 */
@Service
public class RepairInfoServiceImpl extends ServiceImpl<RepairInfoMapper, RepairInfo> implements RepairInfoService {

    @Autowired
    private FileUploadService fileUploadService;

    private static final String PREFIX = "ksdx-";

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd");

    private static final DateTimeFormatter DATE_FORMATTER2 =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @Override
    public PageWrapper<RepairInfoVO> pageVO(RepairInfoPageQueryParam pageQueryParam) {
        MPJLambdaWrapper<RepairInfo> wrapper = new MPJLambdaWrapper<>();

        // 选择字段
        wrapper.selectAll(RepairInfo.class)
                .select(CustomerInfo::getRepairTeam);

        // 左连接Customer表
        wrapper.leftJoin(CustomerInfo.class, CustomerInfo::getId, RepairInfo::getCustomerId);

        wrapper
                .like(StrUtil.isNotBlank(pageQueryParam.getCompanyName()),
                        RepairInfo::getCompanyName, pageQueryParam.getCompanyName())
                .like(StrUtil.isNotBlank(pageQueryParam.getRepairIssues()),
                        RepairInfo::getRepairIssues, pageQueryParam.getRepairIssues())
                .eq(StrUtil.isNotBlank(pageQueryParam.getWorkOrderStatus()),
                        RepairInfo::getWorkOrderStatus, pageQueryParam.getWorkOrderStatus())
                .eq(StrUtil.isNotBlank(pageQueryParam.getWorkOrderType()),
                        RepairInfo::getWorkOrderType, pageQueryParam.getWorkOrderType())
                .like(StrUtil.isNotBlank(pageQueryParam.getName()),
                        RepairInfo::getName, pageQueryParam.getName())
                .eq(StrUtil.isNotBlank(pageQueryParam.getWorkOrderResult()),
                        RepairInfo::getWorkOrderResult, pageQueryParam.getWorkOrderResult())
                .eq(StrUtil.isNotBlank(pageQueryParam.getReviewResult()),
                        RepairInfo::getReviewResult, pageQueryParam.getReviewResult())
                .ge(pageQueryParam.getReportTimeStart() != null,
                        RepairInfo::getReportTime, pageQueryParam.getReportTimeStart())
                .le(pageQueryParam.getReportTimeEnd() != null,
                        RepairInfo::getReportTime, pageQueryParam.getReportTimeEnd())
                // 关键：添加关联表查询条件
                .eq(StrUtil.isNotBlank(pageQueryParam.getRepairTeam()),
                        CustomerInfo::getRepairTeam, pageQueryParam.getRepairTeam());

        // 默认排序
        wrapper.orderByDesc(RepairInfo::getId);

        IPage<RepairInfoVO> pageData = baseMapper.selectJoinPage(
                pageQueryParam.page(),            // Page 对象
                RepairInfoVO.class, // 返回类型
                wrapper          // 查询条件
        );

        List<RepairInfoVO> pageVOList = BeanUtil.copyListProperties(pageData.getRecords(), RepairInfoVO::new);

        // TODO: 根据需要填充字典、部门等信息
        BeanUtil.fillListDataDictField(pageVOList);
        BeanUtil.fillListDeptField(pageVOList);

        return new PageWrapper(pageVOList, pageData.getTotal(), pageData.getSize(), pageData.getCurrent());
    }

    @Override
    public RepairInfoVO getVO(String id) {
        RepairInfo entity = this.getById(id);
        if (entity == null) {
            return null;
        }
        RepairInfoVO vo = new RepairInfoVO();
        BeanUtil.copyProperties(entity, vo);

        List<FileStoreVO> sbfjList = fileUploadService.getFileListBySmall("SBFJ", "BSBXFJ", id);
        List<String> sbfjIdList = sbfjList.stream()
                .map(FileStoreVO::getId)  // 提取 id
                .collect(Collectors.toList());  // 收集为 List

        List<FileStoreVO> wxfjList = fileUploadService.getFileListBySmall("WXFJ", "BSBXFJ", id);
        List<String> wxfjIdList = wxfjList.stream()
                .map(FileStoreVO::getId)  // 提取 id
                .collect(Collectors.toList());  // 收集为 List

        vo.setSbfjList(sbfjIdList);
        vo.setWxfjList(wxfjIdList);

        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData add(RepairInfoAddParam addParam) {
        RepairInfo entity = new RepairInfo();
        BeanUtil.copyProperties(addParam, entity);
        String dateStr = LocalDateTime.now().format(DATE_FORMATTER);
        String dateStr2 = LocalDateTime.now().format(DATE_FORMATTER2);
        int size = this.lambdaQuery()
                .likeRight(RepairInfo::getCreateTime, dateStr2)
                .list().size();
        int nextSequence = size + 1;
        String formattedNext = String.format("%04d", nextSequence);
        String workOrderNO =  PREFIX + dateStr +formattedNext;
        entity.setWorkOrderNo(workOrderNO);
        entity.setWorkOrderStatus("1");
        entity.setWorkOrderType("1");
        entity.setReportTime(LocalDateTime.now());
        entity.setCompanyName(SaUtil.getWxLoginUser().getDeptName());
        entity.setCustomerId(SaUtil.getDeptId());
        this.save(entity);
        String businessId = entity.getId();

        fileUploadService.updateFileBusinessId(businessId,addParam.getFileIds());

        return ResponseData.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData edit(RepairInfoEditParam editParam) {
        RepairInfo entity = this.getById(editParam.getId());
        if (entity == null) {
            ResponseData.fail("数据为空");
        }
        BeanUtil.copyProperties(editParam, entity);

        this.updateById(entity);

        /*fileUploadService.updateFileBusinessId(entity.getId(),editParam.getFileIds());*/

        return ResponseData.success();
    }

    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    public boolean freeze(String id) {
        return this.update(new LambdaUpdateWrapper<RepairInfo>()
                .eq(RepairInfo::getId, id)
                .set(RepairInfo::getWorkOrderStatus, "99"));
    }

}