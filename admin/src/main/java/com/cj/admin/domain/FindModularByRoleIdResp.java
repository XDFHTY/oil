package com.cj.admin.domain;


import com.cj.common.domain.AuthModulars;
import com.cj.common.domain.AuthRoleModulars;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "FindModularByRoleId 接口返回参数")
public class FindModularByRoleIdResp {

    @ApiModelProperty(name = "authModulars",value = "系统内权限",dataType = "AuthModulars")
    private AuthModulars authModulars;


    @ApiModelProperty(name = "authRoleModulars",value = "角色拥有的权限",dataType = "AuthRoleModulars")
    private AuthRoleModulars authRoleModulars;
}
