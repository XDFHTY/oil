package com.cj.admin.domain;

import com.cj.common.entity.AuthRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * IfLogin 接口返回参数实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "IfLogin 接口返回参数")
public class IfLoginResp {

    @ApiModelProperty(name = "token",value = "token",dataType = "String")
    private String token;

    @ApiModelProperty(name = "issuedAt",value = "token签发时间",dataType = "Date")
    private Date issuedAt;

    @ApiModelProperty(name = "roles",value = "用户角色集合",dataType = "List")
    private List<AuthRole> roles;
}
