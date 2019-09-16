package com.cj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述:annual_report表的实体类
 * @version
 * @author:  XD
 * @创建时间: 2018-10-16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "年度环境评估报告对象")
public class AnnualReport implements Serializable {
    /**
     * 年度环境评估报告表
     */
    @ApiModelProperty(name = "annualReportId",value = "Id",dataType = "Long")
    private Long annualReportId;

    /**
     * 分公司id
     */
    @ApiModelProperty(name = "branchCompanyId",value = "分公司id",dataType = "Long")
    private Long branchCompanyId;

    /**
     * 气矿id
     */
    @ApiModelProperty(name = "factoryId",value = "气矿id",dataType = "Long")
    private Long factoryId;

    /**
     * 报告文件路径
     */
    @ApiModelProperty(name = "reportUrl",value = "报告文件路径",dataType = "String")
    private String reportUrl;

    /**
     * 报告文件名
     */
    @ApiModelProperty(name = "reportName",value = "报告文件名",dataType = "String")
    private String reportName;

    /**
     * 年份
     */
    @ApiModelProperty(name = "year",value = "年份",dataType = "String")
    private String year;

    /**
     * 文件分类 1-分公司报告 2-矿区报告
     */
    @ApiModelProperty(name = "type",value = "文件分类 1-分公司报告 2-矿区报告",dataType = "String")
    private String type;

    /**
     * 操作员Id
     */
    @ApiModelProperty(name = "operatorId",value = "操作员Id",dataType = "Long")
    private Long operatorId;

    /**
     * 状态，1-正常，0-禁用（删除），-1-停用
     */
    @ApiModelProperty(name = "reportState",value = "状态，1-正常，0-禁用（删除），-1-停用",dataType = "String")
    private String reportState;

    /**
     * 创建时间
     */
    @ApiModelProperty(name = "createTime",value = "创建时间",dataType = "Date")
    private Date createTime;

    /**
     * 最后更新时间
     */
    @ApiModelProperty(name = "updateTime",value = "最后更新时间",dataType = "Date")
    private Date updateTime;

    /**
     * 年度环境评估报告表
     * @return annual_report_id 年度环境评估报告表
     */
    public Long getAnnualReportId() {
        return annualReportId;
    }

    /**
     * 年度环境评估报告表
     * @param annualReportId 年度环境评估报告表
     */
    public void setAnnualReportId(Long annualReportId) {
        this.annualReportId = annualReportId;
    }

    /**
     * 分公司id
     * @return branch_company_id 分公司id
     */
    public Long getBranchCompanyId() {
        return branchCompanyId;
    }

    /**
     * 分公司id
     * @param branchCompanyId 分公司id
     */
    public void setBranchCompanyId(Long branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }

    /**
     * 气矿id
     * @return factory_id 气矿id
     */
    public Long getFactoryId() {
        return factoryId;
    }

    /**
     * 气矿id
     * @param factoryId 气矿id
     */
    public void setFactoryId(Long factoryId) {
        this.factoryId = factoryId;
    }

    /**
     * 报告文件路径
     * @return report_url 报告文件路径
     */
    public String getReportUrl() {
        return reportUrl;
    }

    /**
     * 报告文件路径
     * @param reportUrl 报告文件路径
     */
    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl == null ? null : reportUrl.trim();
    }

    /**
     * 报告文件名
     * @return report_name 报告文件名
     */
    public String getReportName() {
        return reportName;
    }

    /**
     * 报告文件名
     * @param reportName 报告文件名
     */
    public void setReportName(String reportName) {
        this.reportName = reportName == null ? null : reportName.trim();
    }

    /**
     * 年份
     * @return year 年份
     */
    public String getYear() {
        return year;
    }

    /**
     * 年份
     * @param year 年份
     */
    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
    }

    /**
     * 文件分类 1-分公司报告 2-矿区报告
     * @return type 文件分类 1-分公司报告 2-矿区报告
     */
    public String getType() {
        return type;
    }

    /**
     * 文件分类 1-分公司报告 2-矿区报告
     * @param type 文件分类 1-分公司报告 2-矿区报告
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 操作员Id
     * @return operator_id 操作员Id
     */
    public Long getOperatorId() {
        return operatorId;
    }

    /**
     * 操作员Id
     * @param operatorId 操作员Id
     */
    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * 状态，1-正常，0-禁用（删除），-1-停用
     * @return report_state 状态，1-正常，0-禁用（删除），-1-停用
     */
    public String getReportState() {
        return reportState;
    }

    /**
     * 状态，1-正常，0-禁用（删除），-1-停用
     * @param reportState 状态，1-正常，0-禁用（删除），-1-停用
     */
    public void setReportState(String reportState) {
        this.reportState = reportState == null ? null : reportState.trim();
    }

    /**
     * 创建时间
     * @return create_time 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 最后更新时间
     * @return update_time 最后更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 最后更新时间
     * @param updateTime 最后更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}