package com.cj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "环境监测机构对象")
public class Environmental  implements Serializable {
    /**
     * 环境监测机构信息表
     */
    @ApiModelProperty(name = "brigadeId",value = "id",dataType = "Long")
    private Long environmentalId;

    /**
     * 机构名称
     */
    @ApiModelProperty(name = "environmentalName",value = "机构名称",dataType = "String")
    private String environmentalName;

    /**
     * 服务对象
     */
    @ApiModelProperty(name = "environmentalServer",value = "服务对象",dataType = "String")
    private String environmentalServer;

    /**
     * 机构地址
     */
    @ApiModelProperty(name = "environmentalAddress",value = "机构地址",dataType = "String")
    private String environmentalAddress;

    /**
     * 联系人
     */
    @ApiModelProperty(name = "environmentalLinkman",value = "联系人",dataType = "String")
    private String environmentalLinkman;

    /**
     * 联系人电话
     */
    @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$",message = "电话号码格式错误")
    @NotBlank
    @ApiModelProperty(name = "environmentalLinkmanPhone",value = "联系人电话",dataType = "Long")
    private String environmentalLinkmanPhone;

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
     * 环境监测机构信息表
     * @return environmental_id 环境监测机构信息表
     */
    public Long getEnvironmentalId() {
        return environmentalId;
    }

    /**
     * 环境监测机构信息表
     * @param environmentalId 环境监测机构信息表
     */
    public void setEnvironmentalId(Long environmentalId) {
        this.environmentalId = environmentalId;
    }

    /**
     * 机构名称
     * @return environmental_name 机构名称
     */
    public String getEnvironmentalName() {
        return environmentalName;
    }

    /**
     * 机构名称
     * @param environmentalName 机构名称
     */
    public void setEnvironmentalName(String environmentalName) {
        this.environmentalName = environmentalName == null ? null : environmentalName.trim();
    }

    /**
     * 服务对象
     * @return environmental_server 服务对象
     */
    public String getEnvironmentalServer() {
        return environmentalServer;
    }

    /**
     * 服务对象
     * @param environmentalServer 服务对象
     */
    public void setEnvironmentalServer(String environmentalServer) {
        this.environmentalServer = environmentalServer == null ? null : environmentalServer.trim();
    }

    /**
     * 机构地址
     * @return environmental_address 机构地址
     */
    public String getEnvironmentalAddress() {
        return environmentalAddress;
    }

    /**
     * 机构地址
     * @param environmentalAddress 机构地址
     */
    public void setEnvironmentalAddress(String environmentalAddress) {
        this.environmentalAddress = environmentalAddress == null ? null : environmentalAddress.trim();
    }

    /**
     * 联系人
     * @return environmental_linkman 联系人
     */
    public String getEnvironmentalLinkman() {
        return environmentalLinkman;
    }

    /**
     * 联系人
     * @param environmentalLinkman 联系人
     */
    public void setEnvironmentalLinkman(String environmentalLinkman) {
        this.environmentalLinkman = environmentalLinkman == null ? null : environmentalLinkman.trim();
    }

    /**
     * 联系人电话
     * @return environmental_linkman_phone 联系人电话
     */
    public String getEnvironmentalLinkmanPhone() {
        return environmentalLinkmanPhone;
    }

    /**
     * 联系人电话
     * @param environmentalLinkmanPhone 联系人电话
     */
    public void setEnvironmentalLinkmanPhone(String environmentalLinkmanPhone) {
        this.environmentalLinkmanPhone = environmentalLinkmanPhone;
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