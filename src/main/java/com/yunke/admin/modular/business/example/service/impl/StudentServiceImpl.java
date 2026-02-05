package com.yunke.admin.modular.business.example.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.framework.mybatisplus.base.GeneralServiceImpl;
import com.yunke.admin.modular.business.example.mapper.StudentMapper;
import com.yunke.admin.modular.business.example.model.entity.Student;
import com.yunke.admin.modular.business.example.model.param.StudentAddParam;
import com.yunke.admin.modular.business.example.model.param.StudentEditParam;
import com.yunke.admin.modular.business.example.model.param.StudentPageQueryParam;
import com.yunke.admin.modular.business.example.model.vo.StudentVO;
import com.yunke.admin.modular.business.example.service.StudentService;
import com.yunke.admin.modular.monitor.log.model.entity.LogLogin;
import com.yunke.admin.modular.monitor.log.model.vo.LogLoginVO;
import com.yunke.admin.modular.openapi.model.entity.OpenapiCaller;
import com.yunke.admin.modular.openapi.model.vo.OpenapiCallerVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @className StudentServiceImpl
 * @description: example演示_Service实现类
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/16
 */
@Service
public class StudentServiceImpl extends GeneralServiceImpl<StudentMapper, Student> implements StudentService {

    @Override
    public PageWrapper<StudentVO> pageVO(StudentPageQueryParam pageQueryParam) {
        //单表查询方式1
        LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 查询条件：关键字模糊查询
        if(StrUtil.isNotEmpty(pageQueryParam.getKeyword())){
            lambdaQueryWrapper.like(Student::getName,pageQueryParam.getKeyword());
        }

        // 排序
        lambdaQueryWrapper.orderByAsc(Student::getBirthday);

        IPage<Student> pageData = this.page(pageQueryParam.page(), lambdaQueryWrapper);
        //PageWrapper pageWrapper3 = new PageWrapper<>(pageData);

        List<StudentVO> pageVOList =BeanUtil.copyListProperties(pageData.getRecords(), StudentVO::new);
        //填充字典 可选
        BeanUtil.fillListDataDictField(pageVOList);
        BeanUtil.fillListEnumDictField(pageVOList);
        PageWrapper pageWrapper = new PageWrapper(pageVOList,pageData.getTotal(), pageData.getSize(), pageData.getCurrent());

//        //单表查询方式2
//        MPJLambdaWrapper<Student> mpjLambdaWrapper = new MPJLambdaWrapper();
//        // 查询条件：关键字模糊查询
//        if(StrUtil.isNotEmpty(pageQueryParam.getKeyword())){
//            mpjLambdaWrapper.like(Student::getName,pageQueryParam.getKeyword());
//        }
//
//        // 排序
//        mpjLambdaWrapper.orderByAsc(Student::getBirthday);
//        IPage<StudentVO> pageData2 = this.selectJoinListPage(pageQueryParam.page(), StudentVO.class, mpjLambdaWrapper);
//        //填充字典
//        BeanUtil.fillListDataDictField(pageData2.getRecords());
//        BeanUtil.fillListEnumDictField(pageData2.getRecords());
//        PageWrapper pageWrapper2 = new PageWrapper(pageData2);

        return pageWrapper;
    }

    @Override
    public StudentVO getVO(String id){
        StudentVO studentVO = null;
        Student student = this.getById(id);
        if(student != null){
            studentVO = new StudentVO();
            BeanUtil.copyProperties(student,studentVO);
        }
        return studentVO;
    }

    @Transactional
    @Override
    public boolean add(StudentAddParam studentAddParam) {
        Student student = new Student();
        BeanUtil.copyProperties(student,studentAddParam);
        return this.save(student);
    }

    @Transactional
    @Override
    public boolean edit(StudentEditParam studentEditParam) {
        Student student = new Student();
        BeanUtil.copyProperties(student,studentEditParam);
        return this.updateById(student);
    }

    @Transactional
    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }
}
