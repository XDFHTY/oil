package com.cj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
@ApiModel(value = "消防队分布对象")
public class Brigade  implements Serializable {
    /**
     * 消防队分布表
     */
    @ApiModelProperty(name = "brigadeId",value = "id",dataType = "Long")
    private Long brigadeId;

    /**
     * 队伍名称
     */
    @ApiModelProperty(name = "brigade",value = "队伍名称",dataType = "String")
    private String brigade;

    /**
     * 负责人姓名
     */
    @ApiModelProperty(name = "brigadePerson",value = "负责人姓名",dataType = "String")
    private String brigadePerson;

    /**
     * 负责人办公电话
     */
    //@Pattern(regexp = "^0\\d{2,3}-\\d{7,8}$",message = "电话号码格式错误")
    @NotBlank
    @ApiModelProperty(name = "brigadePersonTelphone",value = "负责人办公电话",dataType = "Long")
    private String brigadePersonTelphone;

    /**
     * 负责人联系方式
     */
    //@Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$",message = "电话号码格式错误")
    @NotBlank
    @ApiModelProperty(name = "brigadePersonPhone",value = "负责人联系方式",dataType = "Long")
    private String brigadePersonPhone;

    /**
     * 厂，矿ID
     */
    @ApiModelProperty(name = "factoryId",value = "厂，矿ID",dataType = "Long")
    private Long factoryId;

    /**
     * 厂站ID
     */
    @ApiModelProperty(name = "stationId",value = "厂站ID",dataType = "Long")
    private Long stationId;

    /**
     * 工作区ID
     */
    @ApiModelProperty(name = "taskAreaId",value = "工作区ID",dataType = "Long")
    private Long taskAreaId;

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
     * 消防队分布表
     * @return brigade_id 消防队分布表
     */
    public Long getBrigadeId() {
        return brigadeId;
    }

    /**
     * 消防队分布表
     * @param brigadeId 消防队分布表
     */
    public void setBrigadeId(Long brigadeId) {
        this.brigadeId = brigadeId;
    }

    /**
     * 队伍名称
     * @return brigade 队伍名称
     */
    public String getBrigade() {
        return brigade;
    }

    /**
     * 队伍名称
     * @param brigade 队伍名称
     */
    public void setBrigade(String brigade) {
        this.brigade = brigade == null ? null : brigade.trim();
    }

    /**
     * 负责人姓名
     * @return brigade_person 负责人姓名
     */
    public String getBrigadePerson() {
        return brigadePerson;
    }

    /**
     * 负责人姓名
     * @param brigadePerson 负责人姓名
     */
    public void setBrigadePerson(String brigadePerson) {
        this.brigadePerson = brigadePerson == null ? null : brigadePerson.trim();
    }

    /**
     * 负责人办公电话
     * @return brigade_person_telphone 负责人办公电话
     */
    public String getBrigadePersonTelphone() {
        return brigadePersonTelphone;
    }

    /**
     * 负责人办公电话
     * @param brigadePersonTelphone 负责人办公电话
     */
    public void setBrigadePersonTelphone(String brigadePersonTelphone) {
        this.brigadePersonTelphone = brigadePersonTelphone;
    }

    /**
     * 负责人联系方式
     * @return brigade_person_phone 负责人联系方式
     */
    public String getBrigadePersonPhone() {
        return brigadePersonPhone;
    }

    /**
     * 负责人联系方式
     * @param brigadePersonPhone 负责人联系方式
     */
    public void setBrigadePersonPhone(String brigadePersonPhone) {
        this.brigadePersonPhone = brigadePersonPhone;
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
     * 厂站ID
     * @return station_id 厂站ID
     */
    public Long getStationId() {
        return stationId;
    }

    /**
     * 厂站ID
     * @param stationId 厂站ID
     */
    public void setStationId(Long stationId) {
        this.stationId = stationId;
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