package com.cj.analysis.pojo;

import com.cj.analysis.annotation.ExcelField;
import com.cj.analysis.annotation.ExcelSheet;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ExcelSheet("统计结果")
@ApiModel(value = "统计结果对象")
public class FactoryEnvironmentView {

    @ExcelField("所属单位")
    @ApiModelProperty(name = "factoryName",value = "所属单位",dataType = "String")
    private String factoryName;
    @ExcelField("所属作业区")
    @ApiModelProperty(name = "taskAreaName",value = "所属作业区",dataType = "String")
    private String taskAreaName;
    @ExcelField("环境风险单元类型")
    @ApiModelProperty(name = "factoryTypeName",value = "环境风险单元类型",dataType = "String")
    private String factoryTypeName;
    @ExcelField("场站名称")
    @ApiModelProperty(name = "stationName",value = "场站名称",dataType = "String")
    private String stationName;
    @ExcelField(value = "结果评估",className = "com.cj.analysis.pojo.EnvironmentResultView")
    @ApiModelProperty(name = "stationName",value = "结果评估",dataType ="<<EnvironmentResultView>>" )
    private EnvironmentResultView environmentResultView;
    @ExcelField("风险等级")
    @ApiModelProperty(name = "result",value = "风险等级",dataType ="String" )
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
