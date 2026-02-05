package com.yunke.admin.modular.business.customer.model.param;

import com.yunke.admin.common.base.BasePageQueryParam;
import lombok.*;

/**
 * @version 1.0
 * @author MCLLB
 * @date 2026/1/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class CustomerInfoPageQueryParam extends BasePageQueryParam {

        private String company;

        private String repairMan;

        private String area;

        private String repairTeam;
}
