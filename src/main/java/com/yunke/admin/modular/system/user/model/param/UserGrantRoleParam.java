package com.yunke.admin.modular.system.user.model.param;

import com.yunke.admin.common.base.BaseParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserGrantRoleParam extends BaseParam {

    /**
     * 用户id
     */
    @NotEmpty(message = "用户id不能为空")
    private String userId;

    /**
     * 角色id集合
     */
    private List<String> roleIdList;

}
