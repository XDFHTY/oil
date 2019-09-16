package com.cj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "预案资料表")
public class ReservePlan  implements Serializable {
    /**
     * 预案资料表
     */
    @ApiModelProperty(name = "reservePlanId",value = "Id ",dataType = "Long")
    private Long reservePlanId;

    /**
     * 工作区
     */
    @ApiModelProperty(name = "taskAreaId",value = "工作区 ",dataType = "Long")
    private Long taskAreaId;

    /**
     * 预案文本路径
     */
    @ApiModelProperty(name = "fileReserve",value = "预案文本路径 ",dataType = "String")
    private String fileReserve;

    /**
     * 是否备案
     */
    @ApiModelProperty(name = "record",value = "是否备案 ",dataType = "String")
    private String record;

    /**
     * 备案时间
     */
    @ApiModelProperty(name = "recordTime",value = "备案时间 ",dataType = "Date")
    private Date recordTime;

    /**
     * 备案原因
     */
    @ApiModelProperty(name = "recordReason",value = "备案原因 ",dataType = "String")
    private String recordReason;

    /**
     * 备案文件路径
     */
    @ApiModelProperty(name = "fileRecord",value = "备案文件路径 ",dataType = "String")
    private String fileRecord;

    /**
     * 预案资料表
     * @return reserve_plan_id 预案资料表
     */
    public Long getReservePlanId() {
        return reservePlanId;
    }

    /**
     * 预案资料表
     * @param reservePlanId 预案资料表
     */
    public void setReservePlanId(Long reservePlanId) {
        this.reservePlanId = reservePlanId;
    }

    /**
     * 工作区
     * @return task_area 工作区
     */
    public Long getTaskAreaId() {
        return taskAreaId;
    }

    /**
     * 工作区
     * @param taskArea 工作区
     */
    public void setTaskArea(Long taskArea) {
        this.taskAreaId = taskAreaId;
    }

    /**
     * 预案文本路径
     * @return file_reserve 预案文本路径
     */
    public String getFileReserve() {
        return fileReserve;
    }

    /**
     * 预案文本路径
     * @param fileReserve 预案文本路径
     */
    public void setFileReserve(String fileReserve) {
        this.fileReserve = fileReserve == null ? null : fileReserve.trim();
    }

    /**
     * 是否备案
     * @return record 是否备案
     */
    public String getRecord() {
        return record;
    }

    /**
     * 是否备案
     * @param record 是否备案
     */
    public void setRecord(String record) {
        this.record = record == null ? null : record.trim();
    }

    /**
     * 备案时间
     * @return record_time 备案时间
     */
    public Date getRecordTime() {
        return recordTime;
    }

    /**
     * 备案时间
     * @param recordTime 备案时间
     */
    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    /**
     * 备案原因
     * @return record_reason 备案原因
     */
    public String getRecordReason() {
        return recordReason;
    }

    /**
     * 备案原因
     * @param recordReason 备案原因
     */
    public void setRecordReason(String recordReason) {
        this.recordReason = recordReason == null ? null : recordReason.trim();
    }

    /**
     * 备案文件路径
     * @return file_record 备案文件路径
     */
    public String getFileRecord() {
        return fileRecord;
    }

    /**
     * 备案文件路径
     * @param fileRecord 备案文件路径
     */
    public void setFileRecord(String fileRecord) {
        this.fileRecord = fileRecord == null ? null : fileRecord.trim();
    }
}