package com.cj.organization.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 账号和组织结构关系 返回对象
 * Created by XD on 2018/11/12.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "AdminAndOrgan 账号和组织结构关系")
public class AdminAndOrgan {
    @ApiModelProperty(name = "adminId",value = "管理员id",dataType = "Long")
    private Long adminId;

    @ApiModelProperty(name = "adminName",value = "管理员账号",dataType = "String")
    private String adminName;

    @ApiModelProperty(name = "roleId",value = "角色id",dataType = "Integer")
    private Integer roleId;

    @ApiModelProperty(name = "roleName",value = "角色名称",dataType = "String")
    private String roleName;

    @ApiModelProperty(name = "popedomGrade",value = "机构类型",dataType = "String")
    private String popedomGrade;

    @ApiModelProperty(name = "fullName",value = "管理员姓名",dataType = "String")
    private String fullName;

    @ApiModelProperty(name = "phone",value = "管理员电话",dataType = "String")
    private String phone;

    @ApiModelProperty(name = "branchCompanyId",value = "分公司id",dataType = "Long")
    private Long branchCompanyId;

    @ApiModelProperty(name = "branchCompanyName",value = "分公司名称",dataType = "String")
    private String branchCompanyName;

    @ApiModelProperty(name = "factoryId",value = "气矿id",dataType = "Long")
    private Long factoryId;

    @ApiModelProperty(name = "factoryName",value = "气矿名称",dataType = "String")
    private String factoryName;

    @ApiModelProperty(name = "taskAreaId",value = "作业区id",dataType = "Long")
    private Long taskAreaId;

    @ApiModelProperty(name = "taskAreaName",value = "作业区名称",dataType = "String")
    private String taskAreaName;

    @ApiModelProperty(name = "stationId",value = "场站id",dataType = "Long")
    private Long stationId;

    @ApiModelProperty(name = "stationName",value = "场站名称",dataType = "String")
    private String stationName;

    //工艺分类id
    @ApiModelProperty(name = "factoryTypeId",value = "工艺分类id",dataType = "Long")
    private Long factoryTypeId;
    //工艺分类名称
    @ApiModelProperty(name = "factoryTypeName",value = "工艺分类名称",dataType = "String")
    private String factoryTypeName;
}
