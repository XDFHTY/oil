package com.cj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "用户—角色关系表")
public class AuthCustomerRole  implements Serializable {
    /**
     * 用户—角色关系表
     */
    @ApiModelProperty(name = "id",value = "ID",dataType = "Long")
    private Long id;

    /**
     * 用户id-包括admin和user ID
     */
    @ApiModelProperty(name = "customerId",value = "用户id-包括admin和user ID",dataType = "Long")
    private Long customerId;

    /**
     * 角色id
     */
    @ApiModelProperty(name = "roleId",value = "角色id",dataType = "Long")
    private Long roleId;

    /**
     * 用户—角色关系表
     * @return id 用户—角色关系表
     */
    public Long getId() {
        return id;
    }

    /**
     * 用户—角色关系表
     * @param id 用户—角色关系表
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 用户id-包括admin和user ID
     * @return customer_id 用户id-包括admin和user ID
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * 用户id-包括admin和user ID
     * @param customerId 用户id-包括admin和user ID
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * 角色id
     * @return role_id 角色id
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 角色id
     * @param roleId 角色id
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}