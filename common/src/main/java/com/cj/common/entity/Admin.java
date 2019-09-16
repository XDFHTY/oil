package com.cj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "管理员信息对象")
public class Admin {
    /**
     * 管理员基础表
     */
    @ApiModelProperty(name = "adminId",value = "Id",dataType = "Long")
    private Long adminId;

    /**
     * 管理员账号
     */
    @ApiModelProperty(name = "adminName",value = "管理员账号",dataType = "String")
    private String adminName;

    /**
     * 管理员密码
     */
    @ApiModelProperty(name = "adminPass",value = "管理员密码",dataType = "String")
    private String adminPass;

    /**
     * 盐值
     */
    @ApiModelProperty(name = "saltVal",value = "盐值",dataType = "String")
    private String saltVal;

    /**
     * 管理员分类，1-系统管理员
     */
    @ApiModelProperty(name = "adminType",value = "管理员分类，1-系统管理员",dataType = "String")
    private String adminType;

    /**
     * 状态，1-正常，0-禁用（删除），-1-停用
     */
    @ApiModelProperty(name = "adminState",value = "状态，1-正常，0-禁用（删除），-1-停用",dataType = "String")
    private String adminState;

    /**
     * 创建时间
     */
    @ApiModelProperty(name = "createTime",value = "创建时间",dataType = "Date")
    private Date createTime;

    /**
     * 操作员ID
     */
    @ApiModelProperty(name = "operatorId",value = "操作员ID",dataType = "Long")
    private Long operatorId;

    /**
     * 最后更新时间
     */
    @ApiModelProperty(name = "updateTime",value = "最后更新时间",dataType = "Date")
    private Date updateTime;

    /**
     * 角色ID
     */
    @ApiModelProperty(name = "roleId",value = "角色ID",dataType = "Long")
    private Long roleId;

}