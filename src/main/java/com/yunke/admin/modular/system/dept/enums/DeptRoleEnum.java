package com.yunke.admin.modular.system.dept.enums;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.Dict;
import com.yunke.admin.common.enums.AbstractDictEnum;
import com.yunke.admin.common.enums.EnumDictTypeConstant;
import com.yunke.admin.framework.core.annotion.EnumDict;
import lombok.Getter;

import java.util.Arrays;


@EnumDict(EnumDictTypeConstant.SYS_DEPT_ROLE)
@Getter
public enum DeptRoleEnum implements AbstractDictEnum {

    /**
     * 组织主要领导
     */
    ORG_LEADER("ORG_LEADER", "组织主要领导"),

    /**
     * 组织一般领导
     */
    ORG_LEADER_NORMAL("ORG_LEADER_NORMAL", "组织一般领导"),

    /**
     * 部门主要领导
     */
    DEPT_LEADER("DEPT_LEADER", "部门主要领导"),

    /**
     * 部门一般领导
     */
    DEPT_LEADER_NORMAL("DEPT_LEADER_NORMAL", "部门一般领导"),

    /**
     * 部门工作人员
     */
    DEPT_WORKER("DEPT_WORKER", "部门工作人员"),

    ;



    private final String code;

    private final String text;

    DeptRoleEnum(String code, String text) {
        this.code = code;
        this.text = text;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getDictType() {
        String dictType = AnnotationUtil.getAnnotationValue(this.getClass(), EnumDict.class);
        return dictType;
    }

    @Override
    public Dict getDict() {
        Dict dict = Dict.create();
        Arrays.stream(DeptRoleEnum.values()).forEach(permissionTypeEnum -> {
            dict.set(permissionTypeEnum.getCode(),permissionTypeEnum.getText());
        });
        return dict;
    }

}
