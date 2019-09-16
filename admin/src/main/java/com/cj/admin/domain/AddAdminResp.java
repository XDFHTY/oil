package com.cj.admin.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "AddAdmin 接口返回参数")
public class AddAdminResp {

    @ApiModelProperty(name = "adminId",value = "管理员ID",dataType = "long")
    private long adminId;
}
