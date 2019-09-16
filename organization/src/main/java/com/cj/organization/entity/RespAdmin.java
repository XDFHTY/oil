package com.cj.organization.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 账号列表 返回实体类
 * Created by XD on 2018/10/12.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "RespAdmin 账号列表返回参数")
public class RespAdmin implements Serializable {
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
     * 场站信息表
     */
    @ApiModelProperty(name = "stationId",value = "场站id",dataType = "Long")
    private Long stationId;
    /**
     * 场站名称
     */
    @ApiModelProperty(name = "stationName",value = "场站名称",dataType = "String")
    private String stationName;
    /**
     * 类型
     */
    @ApiModelProperty(name = "factoryType",value = "工艺类型",dataType = "String")
    private String factoryType;
    /**
     * 管理员id
     */
    @ApiModelProperty(name = "adminId",value = "管理员id",dataType = "Long")
    private Long adminId;
    /**
     * 辖区id
     */
    @ApiModelProperty(name = "popedomGrade",value = "辖区id",dataType = "Integer")
    private Integer popedomGrade;

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

    /**
     * 管理员电话
     */
    @ApiModelProperty(name = "phone",value = "管理员电话",dataType = "String")
    private String phone;

    /**
     * 角色
     */
    @ApiModelProperty(name = "roleName",value = "角色等级",dataType = "String")
    private String roleName;

}
