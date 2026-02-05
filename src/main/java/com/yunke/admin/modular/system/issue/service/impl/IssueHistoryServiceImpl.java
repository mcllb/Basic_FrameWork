package com.yunke.admin.modular.system.issue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.framework.mybatisplus.base.GeneralServiceImpl;
import com.yunke.admin.modular.system.issue.mapper.IssueHistoryMapper;
import com.yunke.admin.modular.system.issue.model.entity.IssueHistory;
import com.yunke.admin.modular.system.issue.model.param.IssueHistoryAddParam;
import com.yunke.admin.modular.system.issue.model.param.IssueHistoryEditParam;
import com.yunke.admin.modular.system.issue.model.param.IssueHistoryPageQueryParam;
import com.yunke.admin.modular.system.issue.model.vo.IssueHistoryVO;
import com.yunke.admin.modular.system.issue.service.IssueHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @className IssueHistoryServiceImpl
 * @description: 缺陷管理历史_Service实现类
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Service
public class IssueHistoryServiceImpl extends GeneralServiceImpl<IssueHistoryMapper, IssueHistory> implements IssueHistoryService {

    @Override
    public PageWrapper<IssueHistoryVO> selectPageVO(IssueHistoryPageQueryParam pageQueryParam) {
        LambdaQueryWrapper<IssueHistory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 查询条件：xxx

        // 查询条件：xxx

        // 排序
        lambdaQueryWrapper.orderByDesc(IssueHistory::getCreateTime);

        Page<IssueHistory> pageData = this.page(pageQueryParam.page(), lambdaQueryWrapper);
        List<IssueHistory> records = pageData.getRecords();
        List<IssueHistoryVO> voList = BeanUtil.copyListProperties(records, IssueHistoryVO::new);
        BeanUtil.fillListDataDictField(voList);
        BeanUtil.fillListEnumDictField(voList);
        PageWrapper<IssueHistoryVO> pageWrapper = new PageWrapper<>(voList, pageData.getTotal(), pageData.getSize(), pageData.getCurrent());
        return pageWrapper;
    }

    @Override
    public List<IssueHistoryVO> listByIssueId(String issueId) {
        List<IssueHistory> list = this.lambdaQuery().eq(IssueHistory::getIssueId, issueId).orderByAsc(IssueHistory::getCreateTime).list();
        List<IssueHistoryVO> voList = BeanUtil.copyListProperties(list, IssueHistoryVO::new);
        BeanUtil.fillListEnumDictField(voList);
        return voList;
    }

    @Override
    public IssueHistoryVO selectVO(String id) {
        IssueHistoryVO issueHistoryVO = new IssueHistoryVO();
        IssueHistory issueHistory = this.getById(id);
        BeanUtil.copyProperties(issueHistory, issueHistoryVO);
        return issueHistoryVO;
    }

    @Transactional
    @Override
    public boolean add(IssueHistoryAddParam addParam) {
        IssueHistory issueHistory = new IssueHistory();
        BeanUtil.copyProperties(addParam, issueHistory);
        return this.save(issueHistory);
    }

    @Transactional
    @Override
    public boolean edit(IssueHistoryEditParam editParam) {
        IssueHistory issueHistory = new IssueHistory();
        BeanUtil.copyProperties(editParam, issueHistory);
        return this.updateById(issueHistory);
    }

    @Transactional
    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }
}
