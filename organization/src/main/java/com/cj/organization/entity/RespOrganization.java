package com.cj.organization.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 组织结构信息返回实体类
 * Created by XD on 2018/10/9.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "RespOrganization 组织结构信息返回实体类")
public class RespOrganization implements Serializable {
    /**
     * 厂、矿基本信息表
     */
    @ApiModelProperty(name = "factoryId",value = "气矿ID",dataType = "Long")
    private Long factoryId;
    /**
     * 厂、矿名称
     */
    @ApiModelProperty(name = "factoryName",value = "气矿名称",dataType = "String")
    private String factoryName;

    /**
     * 作业区信息表
     */
    @ApiModelProperty(name = "taskAreaId",value = "作业区id",dataType = "Long")
    private Long taskAreaId;
    /**
     * 作业区名称
     */
    @ApiModelProperty(name = "taskAreaName",value = "作业区名称",dataType = "String")
    private String taskAreaName;


    /**
     * 管理员基础表
     */
    @ApiModelProperty(name = "adminId",value = "管理员id",dataType = "Long")
    private Long adminId;

    /**
     * 管理员账号
     */
    @ApiModelProperty(name = "adminName",value = "管理员账号",dataType = "String")
    private String adminName;
    /**
     * 管理员姓名
     */
    @ApiModelProperty(name = "fullName",value = "管理员姓名",dataType = "String")
    private String fullName;
}
