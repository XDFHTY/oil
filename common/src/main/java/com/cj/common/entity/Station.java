package com.cj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "场站信息表")
public class Station  implements Serializable {
    /**
     * 场站信息表
     */
    @ApiModelProperty(name = "stationId",value = "场站信息表 ",dataType = "Long")
    private Long stationId;

    /**
     * 作业区ID
     */
    @ApiModelProperty(name = "taskAreaId",value = "作业区ID ",dataType = "Long")
    private Long taskAreaId;

    /**
     * 中心站ID
     */
    @ApiModelProperty(name = "stationCoreId",value = "中心站ID ",dataType = "Long")
    private Long stationCoreId;

    /**
     * 矿分类id
     */
    @ApiModelProperty(name = "factoryTypeId",value = "矿分类id ",dataType = "Long")
    private Long factoryTypeId;

    /**
     * 场站名称
     */
    @ApiModelProperty(name = "stationName",value = "场站名称 ",dataType = "String")
    private String stationName;

    /**
     * 投产年月日
     */
    @ApiModelProperty(name = "productionTime",value = "投产年月日 ",dataType = "Date")
    private Date productionTime;

    /**
     * 辖区等级（账号-辖区关联表用）
     */
    @ApiModelProperty(name = "popedomGrade",value = "辖区等级（账号-辖区关联表用） ",dataType = "Date")
    private String popedomGrade;

    /**
     * 场站信息表
     * @return station_id 场站信息表
     */
    public Long getStationId() {
        return stationId;
    }

    /**
     * 场站信息表
     * @param stationId 场站信息表
     */
    public void setStationId(Long stationId) {
        this.stationId = stationId;
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
     * 中心站ID
     * @return station_core_id 中心站ID
     */
    public Long getStationCoreId() {
        return stationCoreId;
    }

    /**
     * 中心站ID
     * @param stationCoreId 中心站ID
     */
    public void setStationCoreId(Long stationCoreId) {
        this.stationCoreId = stationCoreId;
    }

    /**
     * 矿分类id
     * @return factory_type_id 矿分类id
     */
    public Long getFactoryTypeId() {
        return factoryTypeId;
    }

    /**
     * 矿分类id
     * @param factoryTypeId 矿分类id
     */
    public void setFactoryTypeId(Long factoryTypeId) {
        this.factoryTypeId = factoryTypeId;
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
     * 投产年月日
     * @return production_time 投产年月日
     */
    public Date getProductionTime() {
        return productionTime;
    }

    /**
     * 投产年月日
     * @param productionTime 投产年月日
     */
    public void setProductionTime(Date productionTime) {
        this.productionTime = productionTime;
    }

    /**
     * 辖区等级（账号-辖区关联表用）
     * @return popedom_grade 辖区等级（账号-辖区关联表用）
     */
    public String getPopedomGrade() {
        return popedomGrade;
    }

    /**
     * 辖区等级（账号-辖区关联表用）
     * @param popedomGrade 辖区等级（账号-辖区关联表用）
     */
    public void setPopedomGrade(String popedomGrade) {
        this.popedomGrade = popedomGrade == null ? null : popedomGrade.trim();
    }
}