package com.yunke.admin.common.model;

import cn.hutool.core.lang.Dict;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class DictVO extends Dict implements Serializable {


    private static final long serialVersionUID = 9096712680564481171L;
}
