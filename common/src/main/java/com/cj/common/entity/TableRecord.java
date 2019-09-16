package com.cj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "新增表信息记录")
public class TableRecord  implements Serializable {
    /**
     * 新增表信息记录
     */
    @ApiModelProperty(name = "tableCellRecordId",value = "ID",dataType = "Long")
    private Long tableRecordId;

    /**
     * 场站id
     */
    @ApiModelProperty(name = "stationId",value = "场站id",dataType = "Long")
    private Long stationId;

    /**
     * 表格分类 1-excle动态表 2-风险防控本年度表 3-风险防控上年度表
     */
    @ApiModelProperty(name = "tableType",value = "表格分类 1-excle动态表 2-风险防控本年度表 3-风险防控上年度表",dataType = "String")
    private String tableType;

    /**
     * excle表格信息列表ID
     */
    @ApiModelProperty(name = "excleTableId",value = "excle表格信息列表ID",dataType = "Long")
    private Long excleTableId;

    /**
     * 表格的年份
     */
    @ApiModelProperty(name = "year",value = "表格的年份",dataType = "Date")
    private Date year;

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
     * 修改时间
     */
    @ApiModelProperty(name = "updateTime",value = "操作员ID",dataType = "Date")
    private Date updateTime;

    /**
     * 审核状态，
     * 1-场站填写未提交到作业区（场站可编辑），
     * 2-场站已提交到作业区未审核（场站不可编辑，作业区可编辑），
     * 3-作业区审核未通过被驳回（场站可编辑），
     * 4-作业区已审核未提交到气矿（作业区可编辑），
     * //5-作业区修改场站提交的数据未提交到气矿（作业区可编辑），
     * 6-作业区已提交（已审核通过）气矿未审核（作业区不可编辑，气矿可编辑），
     * 7-气矿已审核未通过被驳回（作业区可编辑），
     * 8-气矿修改作业区提交的数据已审核（气矿可编辑），
     * 9-气矿已提交（已审核通过）（气矿不可编辑），
     * 默认为0
     */
    @ApiModelProperty(name = "checkType",value = "审核状态",dataType = "String")
    private String checkType;

    /**
     * 审核人ID
     */
    @ApiModelProperty(name = "checkId",value = "审核人ID",dataType = "Long")
    private Long checkId;

    /**
     * 审核时间
     */
    @ApiModelProperty(name = "checkTime",value = "审核时间",dataType = "Date")
    private Date checkTime;

    /**
     * 审核意见
     */
    @ApiModelProperty(name = "checkComments",value = "审核意见",dataType = "String")
    private String checkComments;

    /**
     * 新增表信息记录
     * @return table_record_id 新增表信息记录
     */
    public Long getTableRecordId() {
        return tableRecordId;
    }

    /**
     * 新增表信息记录
     * @param tableRecordId 新增表信息记录
     */
    public void setTableRecordId(Long tableRecordId) {
        this.tableRecordId = tableRecordId;
    }

    /**
     * 场站id
     * @return station_id 场站id
     */
    public Long getStationId() {
        return stationId;
    }

    /**
     * 场站id
     * @param stationId 场站id
     */
    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    /**
     * 表格分类 1-excle动态表 2-风险防控本年度表 3-风险防控上年度表
     * @return table_type 表格分类 1-excle动态表 2-风险防控本年度表 3-风险防控上年度表
     */
    public String getTableType() {
        return tableType;
    }

    /**
     * 表格分类 1-excle动态表 2-风险防控本年度表 3-风险防控上年度表
     * @param tableType 表格分类 1-excle动态表 2-风险防控本年度表 3-风险防控上年度表
     */
    public void setTableType(String tableType) {
        this.tableType = tableType == null ? null : tableType.trim();
    }

    /**
     * excle表格信息列表ID
     * @return excle_table_id excle表格信息列表ID
     */
    public Long getExcleTableId() {
        return excleTableId;
    }

    /**
     * excle表格信息列表ID
     * @param excleTableId excle表格信息列表ID
     */
    public void setExcleTableId(Long excleTableId) {
        this.excleTableId = excleTableId;
    }

    /**
     * 表格的年份
     * @return year 表格的年份
     */
    public Date getYear() {
        return year;
    }

    /**
     * 表格的年份
     * @param year 表格的年份
     */
    public void setYear(Date year) {
        this.year = year;
    }

    /**
     * 创建人ID
     * @return founder_id 创建人ID
     */
    public Long getFounderId() {
        return founderId;
    }

    /**
     * 创建人ID
     * @param founderId 创建人ID
     */
    public void setFounderId(Long founderId) {
        this.founderId = founderId;
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
     * 操作员ID
     * @return operator_id 操作员ID
     */
    public Long getOperatorId() {
        return operatorId;
    }

    /**
     * 操作员ID
     * @param operatorId 操作员ID
     */
    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * 修改时间
     * @return update_time 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 修改时间
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 审核状态，1-场站填写未提交到作业区（场站可编辑），2-场站已提交到作业区未审核（场站不可编辑，作业区可编辑），3-作业区审核未通过被驳回（场站可编辑），4-作业区填写未提交到气矿（作业区可编辑），5-作业区修改场站提交的数据未提交到气矿（作业区可编辑），6-作业区已提交（已审核通过）气矿未审核（作业区不可编辑，气矿可编辑），7-气矿已审核未通过被驳回（作业区可编辑），8-气矿修改作业区提交的数据未审核（气矿可编辑），9-气矿已提交（已审核通过）（气矿不可编辑），默认为0
     * @return check_type 审核状态，1-场站填写未提交到作业区（场站可编辑），2-场站已提交到作业区未审核（场站不可编辑，作业区可编辑），3-作业区审核未通过被驳回（场站可编辑），4-作业区填写未提交到气矿（作业区可编辑），5-作业区修改场站提交的数据未提交到气矿（作业区可编辑），6-作业区已提交（已审核通过）气矿未审核（作业区不可编辑，气矿可编辑），7-气矿已审核未通过被驳回（作业区可编辑），8-气矿修改作业区提交的数据未审核（气矿可编辑），9-气矿已提交（已审核通过）（气矿不可编辑），默认为0
     */
    public String getCheckType() {
        return checkType;
    }

    /**
     * 审核状态，1-场站填写未提交到作业区（场站可编辑），2-场站已提交到作业区未审核（场站不可编辑，作业区可编辑），3-作业区审核未通过被驳回（场站可编辑），4-作业区填写未提交到气矿（作业区可编辑），5-作业区修改场站提交的数据未提交到气矿（作业区可编辑），6-作业区已提交（已审核通过）气矿未审核（作业区不可编辑，气矿可编辑），7-气矿已审核未通过被驳回（作业区可编辑），8-气矿修改作业区提交的数据未审核（气矿可编辑），9-气矿已提交（已审核通过）（气矿不可编辑），默认为0
     * @param checkType 审核状态，1-场站填写未提交到作业区（场站可编辑），2-场站已提交到作业区未审核（场站不可编辑，作业区可编辑），3-作业区审核未通过被驳回（场站可编辑），4-作业区填写未提交到气矿（作业区可编辑），5-作业区修改场站提交的数据未提交到气矿（作业区可编辑），6-作业区已提交（已审核通过）气矿未审核（作业区不可编辑，气矿可编辑），7-气矿已审核未通过被驳回（作业区可编辑），8-气矿修改作业区提交的数据未审核（气矿可编辑），9-气矿已提交（已审核通过）（气矿不可编辑），默认为0
     */
    public void setCheckType(String checkType) {
        this.checkType = checkType == null ? null : checkType.trim();
    }

    /**
     * 审核人ID
     * @return check_id 审核人ID
     */
    public Long getCheckId() {
        return checkId;
    }

    /**
     * 审核人ID
     * @param checkId 审核人ID
     */
    public void setCheckId(Long checkId) {
        this.checkId = checkId;
    }

    /**
     * 审核时间
     * @return check_time 审核时间
     */
    public Date getCheckTime() {
        return checkTime;
    }

    /**
     * 审核时间
     * @param checkTime 审核时间
     */
    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    /**
     * 审核意见
     * @return check_comments 审核意见
     */
    public String getCheckComments() {
        return checkComments;
    }

    /**
     * 审核意见
     * @param checkComments 审核意见
     */
    public void setCheckComments(String checkComments) {
        this.checkComments = checkComments == null ? null : checkComments.trim();
    }
}