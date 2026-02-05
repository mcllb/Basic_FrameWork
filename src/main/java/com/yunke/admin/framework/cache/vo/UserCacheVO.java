package com.yunke.admin.framework.cache.vo;

import com.yunke.admin.framework.core.annotion.EnumDictField;
import com.yunke.admin.modular.system.user.enums.UserSexEnum;
import com.yunke.admin.modular.system.user.model.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserCacheVO extends User implements Serializable {

    private static final long serialVersionUID = 909489944743279243L;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 性别
     */
    @EnumDictField(UserSexEnum.class)
    private String sex;
    private String sexText;

}
