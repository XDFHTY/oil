package com.cj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "审核日志记录表")
public class LogCheck  implements Serializable {
    /**
     * 审核日志记录表
     */
    @ApiModelProperty(name = "logCheckId",value = "id",dataType = "Long")
    private Long logCheckId;

    /**
     * 气矿ID
     */
    @ApiModelProperty(name = "factoryId",value = "气矿ID",dataType = "Long")
    private Long factoryId;

    /**
     * 气矿名称
     */
    @ApiModelProperty(name = "factoryName",value = "气矿名称",dataType = "String")
    private String factoryName;

    /**
     * 作业区ID
     */
    @ApiModelProperty(name = "taskAreaId",value = "作业区ID",dataType = "Long")
    private Long taskAreaId;

    /**
     * 作业区名称
     */
    @ApiModelProperty(name = "taskAreaName",value = "作业区名称",dataType = "String")
    private String taskAreaName;

    /**
     * 场站ID
     */
    @ApiModelProperty(name = "stationId",value = "场站ID",dataType = "Long")
    private Long stationId;

    /**
     * 场站名称
     */
    @ApiModelProperty(name = "stationName",value = "场站名称",dataType = "String")
    private String stationName;

    /**
     * 表格年份
     */
    @ApiModelProperty(name = "year",value = "表格年份",dataType = "Date")
    private Date year;

    /**
     * 表格年份，字符串格式
     */
    private String date;

    /**
     * 审核结果
     */
    @ApiModelProperty(name = "checkMsg",value = "审核结果",dataType = "String")
    private String checkMsg;

    /**
     * 审核人用户名
     */
    @ApiModelProperty(name = "adminName",value = "审核结果",dataType = "String")
    private String adminName;

    /**
     * 审核人姓名
     */
    @ApiModelProperty(name = "fullName",value = "审核人姓名",dataType = "String")
    private String fullName;

    /**
     * 审核人ID
     */
    @ApiModelProperty(name = "operatorId",value = "审核人ID",dataType = "Long")
    private Long operatorId;

    @ApiModelProperty(name = "operatorId",value = "审核人当前角色等级",dataType = "Long")
    private String roleGradeName;

    /**
     * 操作时间
     */
    @ApiModelProperty(name = "operatorTime",value = "操作时间",dataType = "Date")
    private Date operatorTime;

    /**
     * 审核意见
     */
    @ApiModelProperty(name = "checkOpinion",value = "审核意见",dataType = "String")
    private String checkOpinion;

}