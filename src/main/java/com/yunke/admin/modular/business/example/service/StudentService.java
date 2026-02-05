package com.yunke.admin.modular.business.example.service;

import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.framework.mybatisplus.base.GeneralService;
import com.yunke.admin.modular.business.example.model.entity.Student;
import com.yunke.admin.modular.business.example.model.param.StudentAddParam;
import com.yunke.admin.modular.business.example.model.param.StudentEditParam;
import com.yunke.admin.modular.business.example.model.param.StudentPageQueryParam;
import com.yunke.admin.modular.business.example.model.vo.StudentVO;

/**
 * @className StudentService
 * @description: example演示_Service接口
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/16
 */
public interface StudentService extends GeneralService<Student> {

    /**
     * @description: 分页查询
     * <p></p>
     * @param pageQueryParam 分页查询请求参数
     * @return PageWrapper<StudentVO>
     * @auth: tianlei
     * @date: 2026/1/16 14:15
     */
    PageWrapper<StudentVO> pageVO(StudentPageQueryParam pageQueryParam);

    /**
     * @description: 获取详情
     * <p></p>
     * @param id 主键id
     * @return StudentVO
     * @auth: tianlei
     * @date: 2026/1/16 14:17
     */
    StudentVO getVO(String id);

    /**
     * @description: 新增
     * <p></p>
     * @param addParam
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/16 14:17
     */
    boolean add(StudentAddParam addParam);

    /**
     * @description: 编辑
     * <p></p>
     * @param editParam
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/16 14:18
     */
    boolean edit(StudentEditParam editParam);

    /**
     * @description: 根据id删除
     * <p></p>
     * @param id 主键id
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/16 14:18
     */
    boolean delete(String id);

}
