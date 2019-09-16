package com.cj.exclepublic.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "findOrganizationResp 接口返回数据")
public class FindOrganizationResp {

    @ApiModelProperty(name = "factoryId",value = "气矿ID",dataType = "Long")
    private Long factoryId;


    @ApiModelProperty(name = "factoryName",value = "气矿名称",dataType = "String")
    private String factoryName;


    @ApiModelProperty(name = "taskAreaId",value = "作业区ID",dataType = "Long")
    private Long taskAreaId;


    @ApiModelProperty(name = "taskAreaName",value = "作业区名称",dataType = "String")
    private String taskAreaName;


    @ApiModelProperty(name = "stationId",value = "场站ID",dataType = "Long")
    private Long stationId;


    @ApiModelProperty(name = "stationName",value = "场站名称",dataType = "String")
    private String stationName;


    @ApiModelProperty(name = "factoryTypeId",value = "工艺分类ID",dataType = "Long")
    private Long factoryTypeId;


    @ApiModelProperty(name = "factoryTypeName",value = "工艺分类名称",dataType = "String")
    private String factoryTypeName;


}
