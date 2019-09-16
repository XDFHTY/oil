package com.cj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "检修队分布表")
public class Overhaul  implements Serializable {
    /**
     * 检修队分布表
     */
    @ApiModelProperty(name = "overhaulId",value = "Id ",dataType = "Long")
    private Long overhaulId;

    /**
     * 队伍名称
     */
    @ApiModelProperty(name = "overhaul",value = "Id ",dataType = "String")
    private String overhaul;

    /**
     * 负责人姓名
     */
    @ApiModelProperty(name = "overhaulPerson",value = "Id ",dataType = "String")
    private String overhaulPerson;

    /**
     * 负责人办公电话
     */
    @Pattern(regexp = "^0\\d{2,3}-\\d{7,8}$",message = "电话号码格式错误")
    @NotBlank(message = "电话号码不能为空")
    @ApiModelProperty(name = "overhaulPersonTelphone",value = "负责人办公电话 ",dataType = "Long")
    private String overhaulPersonTelphone;

    /**
     * 负责人联系方式
     */
    @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$",message = "电话号码格式错误")
    @NotBlank(message = "电话号码不能为空")
    @ApiModelProperty(name = "overhaulPersonPhone",value = "负责人联系方式 ",dataType = "Long")
    private String overhaulPersonPhone;

    /**
     * 厂，矿ID
     */
    @ApiModelProperty(name = "factoryId",value = "厂，矿ID ",dataType = "Long")
    private Long factoryId;

    /**
     * 填表时间
     */
    @ApiModelProperty(name = "tableYear",value = "填表时间",dataType = "Date")
    private Date tableYear;

    /**
     * 填表人名字
     */
    @ApiModelProperty(name = "tableName",value = "填表人名字",dataType = "String")
    private String tableName;

    /**
     * 检修队分布表
     * @return overhaul_id 检修队分布表
     */
    public Long getOverhaulId() {
        return overhaulId;
    }

    /**
     * 检修队分布表
     * @param overhaulId 检修队分布表
     */
    public void setOverhaulId(Long overhaulId) {
        this.overhaulId = overhaulId;
    }

    /**
     * 队伍名称
     * @return overhaul 队伍名称
     */
    public String getOverhaul() {
        return overhaul;
    }

    /**
     * 队伍名称
     * @param overhaul 队伍名称
     */
    public void setOverhaul(String overhaul) {
        this.overhaul = overhaul == null ? null : overhaul.trim();
    }

    /**
     * 负责人姓名
     * @return overhaul_person 负责人姓名
     */
    public String getOverhaulPerson() {
        return overhaulPerson;
    }

    /**
     * 负责人姓名
     * @param overhaulPerson 负责人姓名
     */
    public void setOverhaulPerson(String overhaulPerson) {
        this.overhaulPerson = overhaulPerson == null ? null : overhaulPerson.trim();
    }

    /**
     * 负责人办公电话
     * @return overhaul_person_telphone 负责人办公电话
     */
    public String getOverhaulPersonTelphone() {
        return overhaulPersonTelphone;
    }

    /**
     * 负责人办公电话
     * @param overhaulPersonTelphone 负责人办公电话
     */
    public void setOverhaulPersonTelphone(String overhaulPersonTelphone) {
        this.overhaulPersonTelphone = overhaulPersonTelphone;
    }

    /**
     * 负责人联系方式
     * @return overhaul_person_phone 负责人联系方式
     */
    public String getOverhaulPersonPhone() {
        return overhaulPersonPhone;
    }

    /**
     * 负责人联系方式
     * @param overhaulPersonPhone 负责人联系方式
     */
    public void setOverhaulPersonPhone(String overhaulPersonPhone) {
        this.overhaulPersonPhone = overhaulPersonPhone;
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
     * 填表人名字
     * @return table_name 填表人名字
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * 填表人名字
     * @param tableName 填表人名字
     */
    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }
}