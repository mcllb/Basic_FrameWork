package com.yunke.admin.modular.common.model.param;

import com.yunke.admin.common.base.BaseQueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TestParam extends BaseQueryParam {

    private String req1;
    private String req2;
    private Integer req3;




}
