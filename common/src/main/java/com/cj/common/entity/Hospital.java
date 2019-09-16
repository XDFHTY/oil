package com.cj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "医疗机构表")
public class Hospital  implements Serializable {
    /**
     * 医疗机构表
     */
    @ApiModelProperty(name = "hospitalId",value = "id",dataType = "Long")
    private Long hospitalId;

    /**
     * 机构名称
     */
    @ApiModelProperty(name = "hospitalName",value = "机构名称",dataType = "String")
    private String hospitalName;

    /**
     * 机构地址
     */
    @ApiModelProperty(name = "hospitalAddress",value = "机构地址",dataType = "String")
    private String hospitalAddress;

    /**
     * 机构等级
     */
    @ApiModelProperty(name = "hospitalLevel",value = "机构等级",dataType = "String")
    private String hospitalLevel;

    /**
     * 是否配备高压氧仓(1是，0否)
     */
    @ApiModelProperty(name = "oxygenChamber",value = "是否配备高压氧仓(1是，0否)",dataType = "String")
    private String oxygenChamber;

    /**
     * 厂，矿ID
     */
    @ApiModelProperty(name = "factoryId",value = "厂，矿ID",dataType = "Long")
    private Long factoryId;

    /**
     * 工作区ID
     */
    @ApiModelProperty(name = "taskAreaId",value = "工作区ID",dataType = "Long")
    private Long taskAreaId;

    /**
     * 场站ID
     */
    @ApiModelProperty(name = "stationId",value = "场站ID",dataType = "Long")
    private Long stationId;

    /**
     * 填表时间
     */
    @ApiModelProperty(name = "tableYear",value = "填表时间",dataType = "Date")
    private Date tableYear;

    /**
     * 填表人
     */
    @ApiModelProperty(name = "tableName",value = "填表人",dataType = "String")
    private String tableName;

    /**
     * 医疗机构表
     * @return hospital_id 医疗机构表
     */
    public Long getHospitalId() {
        return hospitalId;
    }

    /**
     * 医疗机构表
     * @param hospitalId 医疗机构表
     */
    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    /**
     * 机构名称
     * @return hospital_name 机构名称
     */
    public String getHospitalName() {
        return hospitalName;
    }

    /**
     * 机构名称
     * @param hospitalName 机构名称
     */
    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName == null ? null : hospitalName.trim();
    }

    /**
     * 机构地址
     * @return hospital_address 机构地址
     */
    public String getHospitalAddress() {
        return hospitalAddress;
    }

    /**
     * 机构地址
     * @param hospitalAddress 机构地址
     */
    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress == null ? null : hospitalAddress.trim();
    }

    /**
     * 机构等级
     * @return hospital_level 机构等级
     */
    public String getHospitalLevel() {
        return hospitalLevel;
    }

    /**
     * 机构等级
     * @param hospitalLevel 机构等级
     */
    public void setHospitalLevel(String hospitalLevel) {
        this.hospitalLevel = hospitalLevel == null ? null : hospitalLevel.trim();
    }

    /**
     * 是否配备高压氧仓(1是，0否)
     * @return oxygen_chamber 是否配备高压氧仓(1是，0否)
     */
    public String getOxygenChamber() {
        return oxygenChamber;
    }

    /**
     * 是否配备高压氧仓(1是，0否)
     * @param oxygenChamber 是否配备高压氧仓(1是，0否)
     */
    public void setOxygenChamber(String oxygenChamber) {
        this.oxygenChamber = oxygenChamber == null ? null : oxygenChamber.trim();
    }

    /**
     * 厂，矿ID
     * @return factory_id 厂，矿ID
     */
    public Long getFactoryId() {
        return factoryId;
    }

    /**
     * 厂，矿ID
     * @param factoryId 厂，矿ID
     */
    public void setFactoryId(Long factoryId) {
        this.factoryId = factoryId;
    }

    /**
     * 工作区ID
     * @return task_area_id 工作区ID
     */
    public Long getTaskAreaId() {
        return taskAreaId;
    }

    /**
     * 工作区ID
     * @param taskAreaId 工作区ID
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
     * 填表时间
     * @return table_year 填表时间
     */
    public Date getTableYear() {
        return tableYear;
    }

    /**
     * 填表时间
     * @param tableYear 填表时间
     */
    public void setTableYear(Date tableYear) {
        this.tableYear = tableYear;
    }

    /**
     * 填表人
     * @return table_name 填表人
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * 填表人
     * @param tableName 填表人
     */
    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }
}