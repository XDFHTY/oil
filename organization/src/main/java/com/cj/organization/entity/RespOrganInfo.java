package com.cj.organization.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 组织结构和账号返回实体类
 * Created by XD on 2018/11/14.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "RespOrganInfo 组织结构和账号返回参数")
public class RespOrganInfo implements Comparable<RespOrganInfo> {
    /**
     * 分公司信息表
     */
    @ApiModelProperty(name = "branchCompanyId",value = "id",dataType = "Long")
    private Long branchCompanyId;

    /**
     * 分公司名称
     */
    @ApiModelProperty(name = "branchCompanyName",value = "分公司名称",dataType = "String")
    private String branchCompanyName;
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

    @ApiModelProperty(name = "factoryTypeName",value = "工艺分类名称",dataType = "String")
    private String factoryTypeName;

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
     * 管理员姓名
     */
    @ApiModelProperty(name = "fullName",value = "管理员姓名",dataType = "String")
    private String fullName;

    //自定义排序规则
    @Override
    public int compareTo(RespOrganInfo o) {
        int i=0;
        if (o.getFactoryId()==null){
            i+=1;
        }
        if (o.getTaskAreaId()==null){
            i+=1;
        }
        if (o.getStationId()==null){
            i+=1;
        }

        int j=0;
        if (this.getFactoryId()==null){
            j+=1;
        }
        if (this.getTaskAreaId()==null){
            j+=1;
        }
        if (this.getStationId()==null){
            j+=1;
        }

        if (i>j){
            return 1;
        }else if (i<j){
            return -1;
        }else {
            return 0;
        }
    }
}
