package com.yunke.admin.modular.home.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MedicineInstSumVO {

    /**
     * 医保机构总数
     */
    private int totalCount;
    /**
     * 药店数
     */
    private int totalCountP;
    /**
     * 医疗单位数
     */
    private int totalCountH;
}
