package com.cj.admin.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "UpdateAdminByAdminPass 接口请求参数")
public class UpdateAdminByAdminPassReq {


    @ApiModelProperty(name = "oldAdminPass",value = "旧密码",dataType = "String")
    private String oldAdminPass;

    @ApiModelProperty(name = "newAdminPass",value = "新密码",dataType = "String")
    private String newAdminPass;
}
