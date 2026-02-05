package com.yunke.admin.modular.monitor.log.model.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunke.admin.common.base.BasePageQueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class LogOperPageQueryParam extends BasePageQueryParam {

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date opTimeStart;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date opTimeEnd;

}
