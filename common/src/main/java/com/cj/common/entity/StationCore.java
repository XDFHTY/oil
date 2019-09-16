package com.cj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "场站信息表")
public class StationCore  implements Serializable {
    /**
     * 中心站信息表
     */
    @ApiModelProperty(name = "stationCoreId",value = "ID",dataType = "Long")
    private Long stationCoreId;

    /**
     * 作业区ID
     */
    @ApiModelProperty(name = "taskAreaId",value = "作业区ID",dataType = "Long")
    private Long taskAreaId;

    /**
     * 分类ID
     */
    @ApiModelProperty(name = "factoryTypeId",value = "分类ID",dataType = "Long")
    private Long factoryTypeId;

    /**
     * 中心站名称
     */
    @ApiModelProperty(name = "stationCoreName",value = "中心站名称",dataType = "String")
    private String stationCoreName;

    /**
     * 辖区等级（账号-辖区关联表用）
     */
    @ApiModelProperty(name = "popedomGrade",value = "辖区等级（账号-辖区关联表用）",dataType = "String")
    private String popedomGrade;

    /**
     * 中心站信息表
     * @return station_core_id 中心站信息表
     */
    public Long getStationCoreId() {
        return stationCoreId;
    }

    /**
     * 中心站信息表
     * @param stationCoreId 中心站信息表
     */
    public void setStationCoreId(Long stationCoreId) {
        this.stationCoreId = stationCoreId;
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
     * 分类ID
     * @return factory_type_id 分类ID
     */
    public Long getFactoryTypeId() {
        return factoryTypeId;
    }

    /**
     * 分类ID
     * @param factoryTypeId 分类ID
     */
    public void setFactoryTypeId(Long factoryTypeId) {
        this.factoryTypeId = factoryTypeId;
    }

    /**
     * 中心站名称
     * @return station_core_name 中心站名称
     */
    public String getStationCoreName() {
        return stationCoreName;
    }

    /**
     * 中心站名称
     * @param stationCoreName 中心站名称
     */
    public void setStationCoreName(String stationCoreName) {
        this.stationCoreName = stationCoreName == null ? null : stationCoreName.trim();
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