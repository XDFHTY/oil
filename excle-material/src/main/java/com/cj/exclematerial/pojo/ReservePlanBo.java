package com.cj.exclematerial.pojo;

import com.cj.exclematerial.annotation.ExcelField;
import com.cj.exclematerial.annotation.ExcelSheet;

import java.util.Date;

@ExcelSheet("预案信息预留表")
public class ReservePlanBo {
    /**
     * 预案资料表
     */
    @ExcelField("编号")
    private Long reservePlanId;

    /**
     * 工作区
     */
    @ExcelField("工作区")
    private Long taskAreaId;

    /**
     * 预案文本路径
     */
    @ExcelField("预案文件")
    private String fileReserve;

    /**
     * 是否备案
     */
    @ExcelField("是否备案")
    private String record;

    /**
     * 备案时间
     */
    private Date recordTime;

    /**
     * 备案原因
     */
    @ExcelField("未备案原因")
    private String recordReason;

    /**
     * 备案文件路径
     */
    @ExcelField("备案文件")
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
    public Long getTaskArea() {
        return taskAreaId;
    }

    /**
     * 工作区
     * @param taskAreaId 工作区
     */
    public void setTaskArea(Long taskAreaId) {
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