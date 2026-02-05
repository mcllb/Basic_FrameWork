package com.yunke.admin.modular.system.dept.enums;


import com.yunke.admin.common.constant.SysExpCodePrefixConstant;
import com.yunke.admin.common.enums.AbstractExceptionEnum;
import com.yunke.admin.framework.core.annotion.ExpEnumCode;
import com.yunke.admin.framework.core.util.ExpEnumCodeUtil;

/**
 * @className DeptExceptionEnum
 * @description: 系统组织机构相关异常枚举
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@ExpEnumCode(SysExpCodePrefixConstant.DEPT_EXCEPTION_CODE)
public enum DeptExceptionEnum implements AbstractExceptionEnum {

    /**
     * 部门不存在
     */
    DEPT_NOT_EXIST(101, "部门不存在"),


    /**
     * 同级部门名称重复
     */
    SAME_DEPT_NAME_REPEAT(102, "同级部门名称重复"),

    /**
     * 该机构下有员工
     */
    CANNOT_DELETE(103, "该部门下有员工，不能删除"),

    /**
     * 父节点不能和本节点一致，请从新选择父节点
     */
    ID_CANT_EQ_PID(104, "父节点不能和本节点一致，请从新选择父节点"),

    PARENT_STATUS_EXCEPTION(105,"父节点状态异常，不能添加添加子节点"),

    CANNOT_EDIT_ROOT(106,"不能修改根部门"),

    CANNOT_DELETE_ROOT(107,"不能删除根部门"),

    DEPT_CODE_REPEAT(108,"部门编号重复");

    private final Integer code;

    private final String message;

    DeptExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return ExpEnumCodeUtil.getExpEnumCode(this.getClass(),code);
    }

    @Override
    public String getMessage() {
        return message;
    }

}
