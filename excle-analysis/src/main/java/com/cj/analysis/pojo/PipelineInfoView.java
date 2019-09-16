package com.cj.analysis.pojo;

import com.cj.analysis.annotation.ExcelField;
import io.swagger.annotations.ApiModelProperty;

public class PipelineInfoView {
    @ExcelField("管线名称")
    @ApiModelProperty(name = "stationName",value = "管线名称",dataType = "String")
    private String stationName;
    @ExcelField("管线起点")
    @ApiModelProperty(name = "startStation",value = "管线起点",dataType = "String")
    private String startStation;
    @ExcelField("管线终点")
    @ApiModelProperty(name = "endStation",value = "管线终点",dataType = "String")
    private String endStation;

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getEndStation() {
        return endStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }
}
