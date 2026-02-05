package com.yunke.admin.modular.system.role.model.param;

import com.yunke.admin.common.base.BaseParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoleGrantUserParam extends BaseParam {

    /**
     * 角色id
     */
    @NotEmpty(message = "角色id不能为空")
    private String roleId;

    /**
     * 用户id集合
     */
    private List<String> userIdList;

}
