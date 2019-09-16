package com.cj.admin.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "UpdateModularByRoleId 接口请求参数")
public class UpdateModularByRoleIdReq {

    @ApiModelProperty(name = "roleId",value = "角色ID",dataType = "Long")
    private Long roleId;

    @ApiModelProperty(name = "modularIds",value = "权限ID集合",dataType = "List")
    private List<Long> modularIds;
}
