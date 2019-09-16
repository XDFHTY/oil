package com.cj.common.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
@ApiModel(value = "公用查询对象")
public class Query {

    @ApiModelProperty(name = "datetime",value = "年份",dataType = "Date")
    private Date datetime;

    @ApiModelProperty(name = "factoryId",value = "厂，矿ID",dataType = "Long")
    private Long factoryId;

    @ApiModelProperty(name = "taskAreaId",value = "工作区ID",dataType = "Long")
    private Long taskAreaId;

    @ApiModelProperty(name = "stationId",value = "场站ID",dataType = "Long")
    private Long stationId;

    @ApiModelProperty(name = "shape",value = "点型或者线型（1点型工程，2线型工程）",dataType = "List")
    private List<Integer> shape;

    public List<Integer> getShape() {
        return shape;
    }

    public void setShape(List<Integer> shape) {
        this.shape = shape;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Long getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(Long factoryId) {
        this.factoryId = factoryId;
    }

    public Long getTaskAreaId() {
        return taskAreaId;
    }

    public void setTaskAreaId(Long taskAreaId) {
        this.taskAreaId = taskAreaId;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }
}
