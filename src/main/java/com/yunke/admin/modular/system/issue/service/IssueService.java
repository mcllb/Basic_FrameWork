package com.yunke.admin.modular.system.issue.service;

import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.framework.mybatisplus.base.GeneralService;
import com.yunke.admin.modular.system.issue.model.entity.Issue;
import com.yunke.admin.modular.system.issue.model.param.*;
import com.yunke.admin.modular.system.issue.model.vo.IssueVO;

/**
 * @className IssueService
 * @description: 缺陷管理_Service接口
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
public interface IssueService extends GeneralService<Issue> {

    /**
     * @description: 缺陷管理_分页查询
     * <p></p>
     * @param pageQueryParam
     * @return com.yunke.admin.common.model.PageWrapper<com.yunke.admin.modular.system.issue.model.vo.IssueVO>
     * @auth: tianlei
     * @date: 2026/1/15 21:05
     */
    PageWrapper<IssueVO> selectPageVO(IssuePageQueryParam pageQueryParam);

    /**
     * @description: 缺陷管理_详情
     * <p></p>
     * @param id
     * @return com.yunke.admin.modular.system.issue.model.vo.IssueVO
     * @auth: tianlei
     * @date: 2026/1/15 21:05
     */
    IssueVO selectVO(String id);

    /**
     * @description: 缺陷管理_新增
     * <p></p>
     * @param addParam
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/15 21:05
     */
    boolean add(IssueAddParam addParam);

    /**
     * @description: 缺陷管理_编辑
     * <p></p>
     * @param editParam
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/15 21:05
     */
    boolean edit(IssueEditParam editParam);

    /**
     * @description: 缺陷管理_根据id删除
     * <p></p>
     * @param id
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/15 21:06
     */
    boolean delete(String id);

    /**
     * @description: 开始修复
     * <p></p>
     * @param id
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/15 21:06
     */
    boolean startRepair(String id);

    /**
     * @description: 修复完成
     * <p></p>
     * @param finishRepairParam
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/15 21:06
     */
    boolean finishRepair(IssueFinishRepairParam finishRepairParam);

    /**
     * @description: 重新打开
     * <p></p>
     * @param reopenParam
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/15 21:06
     */
    boolean reopen(IssueReopenParam reopenParam);

     /**
      * @description: 关闭issue
      * <p></p>
      * @param closeParam
      * @return boolean
      * @auth: tianlei
      * @date: 2026/1/15 21:07
      */
     boolean close(IssueCloseParam closeParam);

}
