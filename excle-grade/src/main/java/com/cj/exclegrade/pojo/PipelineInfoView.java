package com.cj.exclegrade.pojo;


import com.cj.exclegrade.annotation.ExcelField;

public class PipelineInfoView {
    @ExcelField("管线名称")
    private String stationName;
    @ExcelField("管线起点")
    private String startStation;
    @ExcelField("管线终点")
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
