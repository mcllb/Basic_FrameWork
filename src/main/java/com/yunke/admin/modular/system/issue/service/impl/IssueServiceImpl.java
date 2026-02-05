package com.yunke.admin.modular.system.issue.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunke.admin.common.constant.SysFileTypeConstant;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.framework.mybatisplus.base.GeneralServiceImpl;
import com.yunke.admin.modular.system.file.service.FileStoreService;
import com.yunke.admin.modular.system.issue.enums.IssueStatusEnum;
import com.yunke.admin.modular.system.issue.mapper.IssueHistoryMapper;
import com.yunke.admin.modular.system.issue.mapper.IssueMapper;
import com.yunke.admin.modular.system.issue.model.entity.Issue;
import com.yunke.admin.modular.system.issue.model.entity.IssueHistory;
import com.yunke.admin.modular.system.issue.model.param.*;
import com.yunke.admin.modular.system.issue.model.vo.IssueVO;
import com.yunke.admin.modular.system.issue.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @className IssueServiceImpl
 * @description: 缺陷管理_Service实现类
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Service
public class IssueServiceImpl extends GeneralServiceImpl<IssueMapper, Issue> implements IssueService {
    @Autowired
    private IssueHistoryMapper issueHistoryMapper;
    @Autowired
    private FileStoreService fileStoreService;

    @Override
    public PageWrapper<IssueVO> selectPageVO(IssuePageQueryParam pageQueryParam) {
        LambdaQueryWrapper<Issue> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(Issue::getId,
                Issue::getIssueFrom,
                Issue::getTitle,
                Issue::getOs,
                Issue::getBrowser,
                Issue::getIssueType,
                Issue::getIssueLevel,
                Issue::getPriorityLevel,
                Issue::getReproSteps,
                Issue::getOpenTime,
                Issue::getCloseTime,
                Issue::getRemark,
                Issue::getStatus,
                Issue::getCreateBy,
                Issue::getCreateTime,
                Issue::getUpdateBy,
                Issue::getUpdateTime
        );

        // 查询条件：标题
        if(StrUtil.isNotEmpty(pageQueryParam.getTitle())){
            lambdaQueryWrapper.like(Issue::getTitle, pageQueryParam.getTitle());
        }

        // 查询条件：优先级
        if(StrUtil.isNotEmpty(pageQueryParam.getPriorityLevel())){
            lambdaQueryWrapper.eq(Issue::getPriorityLevel, pageQueryParam.getPriorityLevel());
        }

        // 查询条件：缺陷来源
        if(StrUtil.isNotEmpty(pageQueryParam.getIssueFrom())){
            lambdaQueryWrapper.eq(Issue::getIssueFrom, pageQueryParam.getIssueFrom());
        }


        // 排序
        lambdaQueryWrapper.orderByDesc(Issue::getCreateTime);

        Page<Issue> pageData = this.page(pageQueryParam.page(), lambdaQueryWrapper);
        List<Issue> records = pageData.getRecords();
        List<IssueVO> voList = BeanUtil.copyListProperties(records, IssueVO::new);
        BeanUtil.fillListDataDictField(voList);
        BeanUtil.fillListEnumDictField(voList);
        PageWrapper<IssueVO> pageWrapper = new PageWrapper<>(voList, pageData.getTotal(), pageData.getSize(), pageData.getCurrent());
        return pageWrapper;
    }

    @Override
    public IssueVO selectVO(String id){
        IssueVO issueVO = new IssueVO();
        Issue issue = this.getById(id);
        BeanUtil.copyProperties(issue,issueVO);
        return issueVO;
    }

    @Transactional
    @Override
    public boolean add(IssueAddParam addParam) {
        Issue issue = new Issue();
        BeanUtil.copyProperties(addParam,issue);
        issue.setStatus(IssueStatusEnum.OPEN.getCode());
        issue.setOpenTime(new Date());
        boolean ret = this.save(issue);
        if(ret){
            IssueHistory issueHistory = new IssueHistory();
            issueHistory.setIssueId(issue.getId());
            issueHistory.setStatus(IssueStatusEnum.OPEN.getCode());
            issueHistoryMapper.insert(issueHistory);
        }
        return ret;
    }

    @Transactional
    @Override
    public boolean edit(IssueEditParam editParam) {
        Issue issue = new Issue();
        BeanUtil.copyProperties(editParam,issue);
        issue.setStatus(null);
        return this.updateById(issue);
    }

    @Transactional
    @Override
    public boolean delete(String id) {
        boolean ret = this.removeById(id);
        if(ret){
            // 删除过程表
            LambdaQueryWrapper<IssueHistory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(IssueHistory::getIssueId, id);
            issueHistoryMapper.delete(lambdaQueryWrapper);
            // 异步删除附件
            ThreadUtil.execAsync(new Runnable() {
                @Override
                public void run() {
                    fileStoreService.deleteWithBusinessIdAndFileType(id, SysFileTypeConstant.ISSUEFJ);
                }
            });
        }
        return ret;
    }

    @Override
    public boolean startRepair(String id) {
        Issue issue = new Issue();
        issue.setId(id);
        issue.setStatus(IssueStatusEnum.REPAIRING.getCode());
        boolean ret = this.updateById(issue);
        if(ret){
            IssueHistory issueHistory = new IssueHistory();
            issueHistory.setIssueId(issue.getId());
            issueHistory.setStatus(IssueStatusEnum.REPAIRING.getCode());
            issueHistoryMapper.insert(issueHistory);
        }
        return ret;
    }

    @Override
    public boolean finishRepair(IssueFinishRepairParam finishRepairParam) {
        Issue issue = new Issue();
        issue.setId(finishRepairParam.getId());
        issue.setStatus(IssueStatusEnum.REPAIRED.getCode());
        boolean ret = this.updateById(issue);
        if(ret){
            IssueHistory issueHistory = new IssueHistory();
            issueHistory.setIssueId(issue.getId());
            issueHistory.setStatus(IssueStatusEnum.REPAIRED.getCode());
            issueHistory.setComment(finishRepairParam.getComment());
            issueHistoryMapper.insert(issueHistory);
        }
        return ret;
    }

    @Override
    public boolean reopen(IssueReopenParam reopenParam) {
        Issue issue = new Issue();
        issue.setId(reopenParam.getId());
        issue.setStatus(IssueStatusEnum.REOPEN.getCode());
        boolean ret = this.updateById(issue);
        if(ret){
            IssueHistory issueHistory = new IssueHistory();
            issueHistory.setIssueId(issue.getId());
            issueHistory.setStatus(IssueStatusEnum.REOPEN.getCode());
            issueHistory.setComment(reopenParam.getComment());
            issueHistoryMapper.insert(issueHistory);
        }
        return ret;
    }

    @Override
    public boolean close(IssueCloseParam closeParam) {
        Issue issue = new Issue();
        issue.setId(closeParam.getId());
        issue.setCloseTime(new Date());
        issue.setStatus(IssueStatusEnum.CLOSE.getCode());
        boolean ret = this.updateById(issue);
        if(ret){
            IssueHistory issueHistory = new IssueHistory();
            issueHistory.setIssueId(issue.getId());
            issueHistory.setStatus(IssueStatusEnum.CLOSE.getCode());
            issueHistory.setComment(closeParam.getComment());
            issueHistoryMapper.insert(issueHistory);
        }
        return ret;
    }
}
