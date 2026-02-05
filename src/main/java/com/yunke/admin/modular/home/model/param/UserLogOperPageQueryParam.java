package com.yunke.admin.modular.home.model.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yunke.admin.common.base.BasePageQueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 用户操作日志分页查询请求参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserLogOperPageQueryParam extends BasePageQueryParam {

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date opTimeStart;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date opTimeEnd;

}
