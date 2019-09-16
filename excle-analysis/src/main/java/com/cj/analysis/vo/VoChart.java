package com.cj.analysis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
@ApiModel(value = "数据成图传参列表")
public class VoChart {
    @ApiModelProperty(value="factoryId数组",hidden=true)
    private Long[] factoryIds;
    private List<Long> factoryIdList;
    @ApiModelProperty(value="taskAreaId数组",hidden=true)
    private Long[] taskAreaIds;
    private List<Long> taskAreaIdList;
    @ApiModelProperty(name = "datetime",value = "时间",dataType = "String")
    private String datetime;
    @ApiModelProperty(name = "shape",value = "点型，线型（1表示点型，2表示线型）",dataType = "Integer")
    private Integer shape;

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

    public Integer getShape() {
        return shape;
    }

    public void setShape(Integer shape) {
        this.shape = shape;
    }
}
