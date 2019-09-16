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
@ApiModel(value = "上年度环境风险防控措表")
public class MeasuresLast implements Serializable {
    /**
     * 上年度环境风险防控措表
     */
    @ApiModelProperty(name = "measuresId",value = "Id ",dataType = "Long")
    private Long lastMeasuresId;

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
     * 上年度制定的环境风险防控措施计划
     */
    @ApiModelProperty(name = "lastMeasures",value = "上年度制定的环境风险防控措施计划",dataType = "String")
    private String lastMeasures;

    /**
     * 已经完成整改的环境风险防控措施
     */
    @ApiModelProperty(name = "measuresCompleted",value = "已经完成整改的环境风险防控措施",dataType = "String")
    private String measuresCompleted;

    /**
     * 正在落实（尚未完成）的措施内容
     */
    @ApiModelProperty(name = "measuresContent",value = "正在落实（尚未完成）的措施内容",dataType = "String")
    private String measuresContent;

    /**
     * 正在落实（尚未完成）的责任人
     */
    @ApiModelProperty(name = "lastPersonLiable",value = "正在落实（尚未完成）的责任人",dataType = "String")
    private String lastPersonLiable;

    /**
     * 正在落实（尚未完成）的预计完成时间
     */
    @ApiModelProperty(name = "completionTime",value = "正在落实（尚未完成）的预计完成时间",dataType = "Date")
    private Date completionTime;

    /**
     * 未开始，已纳入后续环境风险防控措施计划
     */
    @ApiModelProperty(name = "noStartPlan",value = "未开始，已纳入后续环境风险防控措施计划",dataType = "String")
    private String noStartPlan;

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
     * 操作员ID
     */
    @ApiModelProperty(name = "operatorId",value = "操作员ID",dataType = "Long")
    private Long operatorId;

    /**
     * 最后更新时间
     */
    @ApiModelProperty(name = "operatorId",value = "最后更新时间",dataType = "Date")
    private Date updateTime;


    private String factoryName;
    private String taskAreaName;
    private String stationName;


}