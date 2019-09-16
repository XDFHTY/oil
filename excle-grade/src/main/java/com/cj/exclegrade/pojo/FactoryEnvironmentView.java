package com.cj.exclegrade.pojo;


import com.cj.exclegrade.annotation.ExcelField;
import com.cj.exclegrade.annotation.ExcelSheet;

@ExcelSheet("统计图")
public class FactoryEnvironmentView {

    @ExcelField("所属单位")
    private String factoryName;
    @ExcelField("所属作业区")
    private String taskAreaName;
    @ExcelField("环境风险单元类型")
    private String factoryTypeName;
    @ExcelField("场站名称")
    private String stationName;
    @ExcelField(value = "结果评估",className = "com.cj.analysis.pojo.EnvironmentResultView")
    private EnvironmentResultView environmentResultView;
    @ExcelField("风险等级")
    private String result;

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getTaskAreaName() {
        return taskAreaName;
    }

    public void setTaskAreaName(String taskAreaName) {
        this.taskAreaName = taskAreaName;
    }

    public String getFactoryTypeName() {
        return factoryTypeName;
    }

    public void setFactoryTypeName(String factoryTypeName) {
        this.factoryTypeName = factoryTypeName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public EnvironmentResultView getEnvironmentResultView() {
        return environmentResultView;
    }

    public void setEnvironmentResultView(EnvironmentResultView environmentResultView) {
        this.environmentResultView = environmentResultView;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
