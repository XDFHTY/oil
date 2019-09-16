package com.cj.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 系统所有角色的所有权限
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "角色类")
public class AuthRoleModulars  implements Serializable {

    //角色ID
    @ApiModelProperty(name = "roleId",value = "角色ID",dataType = "Long")
    private Long roleId;

    //角色名称
    @ApiModelProperty(name = "roleName",value = "角色名称",dataType = "String")
    private String roleName;

    //角色状态
    @ApiModelProperty(name = "roleState",value = "角色状态",dataType = "String")
    private String roleState;



    /**
     * 角色所有的权限ID
     */
    @ApiModelProperty(name = "modularIds",value = "角色所有的权限ID",dataType = "List")
    private List<Modular> modularIds;

    /**
     * 角色下所有权限
     */
    @ApiModelProperty(name = "authModulars",value = "角色下所有权限",dataType = "List")
    private AuthModulars authModulars;

    /**
     * 角色所有的权限ID
     */
    @ApiModelProperty(name = "ids",value = "角色所有的权限ID",dataType = "List")
    private List<Long> ids;
}
