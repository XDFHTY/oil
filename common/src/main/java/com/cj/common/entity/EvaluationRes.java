package com.cj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "环境等级评定对象")
public class EvaluationRes  implements Serializable {
    /**
     * 评估结果表
     */
    @ApiModelProperty(name = "evaluationRes",value = "id",dataType = "Long")
    private Long evaluationRes;

    /**
     * 气矿ID
     */
    @ApiModelProperty(name = "factoryId",value = "气矿ID",dataType = "Long")
    private Long factoryId;

    /**
     * 作业区ID
     */
    @ApiModelProperty(name = "taskAreaId",value = "气矿ID",dataType = "Long")
    private Long taskAreaId;

    /**
     * 场站ID
     */
    @ApiModelProperty(name = "stationId",value = "场站ID",dataType = "Long")
    private Long stationId;

    /**
     * 气矿名字
     */
    @ApiModelProperty(name = "factoryName",value = "气矿名字",dataType = "String")
    private String factoryName;

    /**
     * 作业区名字
     */
    @ApiModelProperty(name = "taskAreaName",value = "作业区名字",dataType = "String")
    private String taskAreaName;

    /**
     * 场站名称
     */
    @ApiModelProperty(name = "stationName",value = "场站名称",dataType = "String")
    private String stationName;

    /**
     * 厂、矿工艺ID
     */
    @ApiModelProperty(name = "factoryTypeId",value = "厂、矿工艺ID",dataType = "Long")
    private Long factoryTypeId;

    /**
     * 工艺名称
     */
    @ApiModelProperty(name = "factoryTypeName",value = "工艺名称",dataType = "String")
    private String factoryTypeName;

    /**
     * 评估所属年份
     */
    @ApiModelProperty(name = "time",value = "评估所属年份",dataType = "Date")
    private Date time;

    /**
     * 水环境风险Q或L
     */
    @ApiModelProperty(name = "waterQl",value = "水环境风险Q或L",dataType = "String")
    private String waterQl;

    /**
     * 水环境风险E
     */
    @ApiModelProperty(name = "waterE",value = "水环境风险E",dataType = "String")
    private String waterE;

    /**
     * 水环境风险M
     */
    @ApiModelProperty(name = "waterM",value = "水环境风险M",dataType = "String")
    private String waterM;

    /**
     * 大气环境风险Q或L
     */
    @ApiModelProperty(name = "gasQl",value = "大气环境风险Q或L",dataType = "String")
    private String gasQl;

    /**
     * 大气环境风险E
     */
    @ApiModelProperty(name = "gasE",value = "大气环境风险E",dataType = "String")
    private String gasE;

    /**
     * 大气环境风险M
     */
    @ApiModelProperty(name = "gasM",value = "大气环境风险M",dataType = "String")
    private String gasM;

    /**
     * 评估结果
     */
    @ApiModelProperty(name = "evaResult",value = "评估结果",dataType = "String")
    private String evaResult;

    /**
     * 评估结果表
     * @return evaluation_res 评估结果表
     */
    public Long getEvaluationRes() {
        return evaluationRes;
    }

    /**
     * 评估结果表
     * @param evaluationRes 评估结果表
     */
    public void setEvaluationRes(Long evaluationRes) {
        this.evaluationRes = evaluationRes;
    }

    /**
     * 气矿ID
     * @return factory_id 气矿ID
     */
    public Long getFactoryId() {
        return factoryId;
    }

    /**
     * 气矿ID
     * @param factoryId 气矿ID
     */
    public void setFactoryId(Long factoryId) {
        this.factoryId = factoryId;
    }

    /**
     * 作业区ID
     * @return task_area_id 作业区ID
     */
    public Long getTaskAreaId() {
        return taskAreaId;
    }

    /**
     * 作业区ID
     * @param taskAreaId 作业区ID
     */
    public void setTaskAreaId(Long taskAreaId) {
        this.taskAreaId = taskAreaId;
    }

    /**
     * 场站ID
     * @return station_id 场站ID
     */
    public Long getStationId() {
        return stationId;
    }

    /**
     * 场站ID
     * @param stationId 场站ID
     */
    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    /**
     * 气矿名字
     * @return factory_name 气矿名字
     */
    public String getFactoryName() {
        return factoryName;
    }

    /**
     * 气矿名字
     * @param factoryName 气矿名字
     */
    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName == null ? null : factoryName.trim();
    }

    /**
     * 作业区名字
     * @return task_area_name 作业区名字
     */
    public String getTaskAreaName() {
        return taskAreaName;
    }

    /**
     * 作业区名字
     * @param taskAreaName 作业区名字
     */
    public void setTaskAreaName(String taskAreaName) {
        this.taskAreaName = taskAreaName == null ? null : taskAreaName.trim();
    }

    /**
     * 场站名称
     * @return station_name 场站名称
     */
    public String getStationName() {
        return stationName;
    }

    /**
     * 场站名称
     * @param stationName 场站名称
     */
    public void setStationName(String stationName) {
        this.stationName = stationName == null ? null : stationName.trim();
    }

    /**
     * 厂、矿工艺ID
     * @return factory_type_id 厂、矿工艺ID
     */
    public Long getFactoryTypeId() {
        return factoryTypeId;
    }

    /**
     * 厂、矿工艺ID
     * @param factoryTypeId 厂、矿工艺ID
     */
    public void setFactoryTypeId(Long factoryTypeId) {
        this.factoryTypeId = factoryTypeId;
    }

    /**
     * 工艺名称
     * @return factory_type_name 工艺名称
     */
    public String getFactoryTypeName() {
        return factoryTypeName;
    }

    /**
     * 工艺名称
     * @param factoryTypeName 工艺名称
     */
    public void setFactoryTypeName(String factoryTypeName) {
        this.factoryTypeName = factoryTypeName == null ? null : factoryTypeName.trim();
    }

    /**
     * 评估所属年份
     * @return time 评估所属年份
     */
    public Date getTime() {
        return time;
    }

    /**
     * 评估所属年份
     * @param time 评估所属年份
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * 水环境风险Q或L
     * @return water_ql 水环境风险Q或L
     */
    public String getWaterQl() {
        return waterQl;
    }

    /**
     * 水环境风险Q或L
     * @param waterQl 水环境风险Q或L
     */
    public void setWaterQl(String waterQl) {
        this.waterQl = waterQl == null ? null : waterQl.trim();
    }

    /**
     * 水环境风险E
     * @return water_e 水环境风险E
     */
    public String getWaterE() {
        return waterE;
    }

    /**
     * 水环境风险E
     * @param waterE 水环境风险E
     */
    public void setWaterE(String waterE) {
        this.waterE = waterE == null ? null : waterE.trim();
    }

    /**
     * 水环境风险M
     * @return water_m 水环境风险M
     */
    public String getWaterM() {
        return waterM;
    }

    /**
     * 水环境风险M
     * @param waterM 水环境风险M
     */
    public void setWaterM(String waterM) {
        this.waterM = waterM == null ? null : waterM.trim();
    }

    /**
     * 大气环境风险Q或L
     * @return gas_ql 大气环境风险Q或L
     */
    public String getGasQl() {
        return gasQl;
    }

    /**
     * 大气环境风险Q或L
     * @param gasQl 大气环境风险Q或L
     */
    public void setGasQl(String gasQl) {
        this.gasQl = gasQl == null ? null : gasQl.trim();
    }

    /**
     * 大气环境风险E
     * @return gas_e 大气环境风险E
     */
    public String getGasE() {
        return gasE;
    }

    /**
     * 大气环境风险E
     * @param gasE 大气环境风险E
     */
    public void setGasE(String gasE) {
        this.gasE = gasE == null ? null : gasE.trim();
    }

    /**
     * 大气环境风险M
     * @return gas_m 大气环境风险M
     */
    public String getGasM() {
        return gasM;
    }

    /**
     * 大气环境风险M
     * @param gasM 大气环境风险M
     */
    public void setGasM(String gasM) {
        this.gasM = gasM == null ? null : gasM.trim();
    }

    /**
     * 评估结果
     * @return eva_result 评估结果
     */
    public String getEvaResult() {
        return evaResult;
    }

    /**
     * 评估结果
     * @param evaResult 评估结果
     */
    public void setEvaResult(String evaResult) {
        this.evaResult = evaResult == null ? null : evaResult.trim();
    }
}