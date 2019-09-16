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
@ApiModel(value = "本年度环境风险防控措表")
public class Measures  implements Serializable {
    /**
     * 本年度环境风险防控措表
     */
    @ApiModelProperty(name = "measuresId",value = "Id ",dataType = "Long")
    private Long measuresId;

    /**
     * 场站id
     */
    @ApiModelProperty(name = "stationId",value = "场站id ",dataType = "Long")
    private Long stationId;

    /**
     * 年份
     */
    @ApiModelProperty(name = "year",value = "年份 ",dataType = "Date")
    private Date year;

    /**
     * 现有环境风险防控措施
     */
    @ApiModelProperty(name = "existingMeasures",value = "现有环境风险防控措施",dataType = "String")
    private String existingMeasures;

    /**
     * 本年度拟整改的措施
     */
    @ApiModelProperty(name = "proposedRectification",value = "本年度拟整改的措施",dataType = "String")
    private String proposedRectification;

    /**
     * 本年度整改措施计划完成时间
     */
    @ApiModelProperty(name = "plannedTime",value = "本年度整改措施计划完成时间",dataType = "Date")
    private Date plannedTime;

    /**
     * 本年度措施整改责任部门
     */
    @ApiModelProperty(name = "department",value = "本年度措施整改责任部门",dataType = "String")
    private String department;

    /**
     * 本年度措施整改责任人
     */
    @ApiModelProperty(name = "personLiable",value = "本年度措施整改责任人",dataType = "String")
    private String personLiable;

    /**
     * 状态，1-正常，0-禁用（删除），-1-停用
     */
    @ApiModelProperty(name = "state",value = "状态，1-正常，0-禁用（删除），-1-停用",dataType = "String")
    private String state;

    /**
     * 创建人ID
     */
    @ApiModelProperty(name = "founderId",value = "创建人ID",dataType = "Long")
    private Long founderId;

    /**
     * 创建时间
     */
    @ApiModelProperty(name = "createTime",value = "创建时间",dataType = "Date")
    private Date createTime;

    /**
     * 操作员Id
     */
    @ApiModelProperty(name = "operatorId",value = "操作员Id",dataType = "Long")
    private Long operatorId;

    /**
     * 最后更新时间
     */
    private Date updateTime;

    private String factoryName;
    private String taskAreaName;
    private String stationName;
}