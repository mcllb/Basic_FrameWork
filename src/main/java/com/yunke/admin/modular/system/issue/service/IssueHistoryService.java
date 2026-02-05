package com.yunke.admin.modular.system.issue.service;

import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.framework.mybatisplus.base.GeneralService;
import com.yunke.admin.modular.system.issue.model.entity.IssueHistory;
import com.yunke.admin.modular.system.issue.model.param.IssueHistoryAddParam;
import com.yunke.admin.modular.system.issue.model.param.IssueHistoryEditParam;
import com.yunke.admin.modular.system.issue.model.param.IssueHistoryPageQueryParam;
import com.yunke.admin.modular.system.issue.model.vo.IssueHistoryVO;

import java.util.List;

/**
 * @className IssueHistoryService
 * @description: 缺陷管理历史_Service接口
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
public interface IssueHistoryService extends GeneralService<IssueHistory> {
	
   /**
    * @description: 缺陷管理历史_分页查询
    * <p></p>
    * @param pageQueryParam
    * @return com.yunke.admin.common.model.PageWrapper<com.yunke.admin.modular.system.issue.model.vo.IssueHistoryVO>
    * @auth: tianlei
    * @date: 2026/1/15 21:03
    */
    PageWrapper<IssueHistoryVO> selectPageVO(IssueHistoryPageQueryParam pageQueryParam);

    /**
     * @description: 根据缺陷主键查询缺陷历史列表
     * <p></p>
     * @param issueId
     * @return java.util.List<com.yunke.admin.modular.system.issue.model.vo.IssueHistoryVO>
     * @auth: tianlei
     * @date: 2026/1/15 21:03
     */
    List<IssueHistoryVO> listByIssueId(String issueId);
	
   /**
    * @description: 缺陷管理历史_详情
    * <p></p>
    * @param id
    * @return com.yunke.admin.modular.system.issue.model.vo.IssueHistoryVO
    * @auth: tianlei
    * @date: 2026/1/15 21:04
    */
    IssueHistoryVO selectVO(String id);
    
    /**
     * @description: 缺陷管理历史_新增
     * <p></p>
     * @param addParam
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/15 21:04
     */
    boolean add(IssueHistoryAddParam addParam);
	
    /**
     * @description: 缺陷管理历史_编辑
     * <p></p>
     * @param editParam
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/15 21:04
     */
    boolean edit(IssueHistoryEditParam editParam);
	
   /**
    * @description: 缺陷管理历史_根据id删除
    * <p></p>
    * @param id
    * @return boolean
    * @auth: tianlei
    * @date: 2026/1/15 21:04
    */
    boolean delete(String id);

}
