package com.cj.analysis.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class VoLedger {
    @ApiModelProperty(value="taskAreaId数组",hidden=true)
    private Long[] factoryIds;
    private List<Long> factoryIdList;
    @ApiModelProperty(value="taskAreaId数组",hidden=true)
    private Long[] taskAreaIds;
    private List<Long> taskAreaIdList;
    @ApiModelProperty(name = "datetime",value = "时间",dataType = "String")
    private String datetime;
    @ApiModelProperty(name = "pageSize",value = "每页大小",dataType = "Integer")
    private Integer pageSize=10;
    @ApiModelProperty(name = "currentPage",value = "当前页",dataType = "Integer")
    private Integer currentPage=0;
    @ApiModelProperty(name = "grade",value = "0-一，1-较大，2-重大)",dataType = "Integer")
    private Integer grade;

    public Long[] getFactoryIds() {
        return factoryIds;
    }

    public void setFactoryIds(Long[] factoryIds) {
        this.factoryIds = factoryIds;
    }

    public List<Long> getFactoryIdList() {
        return factoryIdList;
    }

    public void setFactoryIdList(List<Long> factoryIdList) {
        this.factoryIdList = factoryIdList;
    }

    public Long[] getTaskAreaIds() {
        return taskAreaIds;
    }

    public void setTaskAreaIds(Long[] taskAreaIds) {
        this.taskAreaIds = taskAreaIds;
    }

    public List<Long> getTaskAreaIdList() {
        return taskAreaIdList;
    }

    public void setTaskAreaIdList(List<Long> taskAreaIdList) {
        this.taskAreaIdList = taskAreaIdList;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
