package com.cj.analysis.pojo;

import com.cj.analysis.annotation.ExcelField;
import com.cj.analysis.annotation.ExcelSheet;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 管线结果集汇总
 */
@ExcelSheet("管线结果汇总表")
@ApiModel(value = "统计图结果对象")
public class FactoryEnvironmentPipelineView {
    @ExcelField("所属单位")
    @ApiModelProperty(name = "factoryName",value = "所属单位",dataType = "String")
    private String factoryName;
    @ExcelField("所属作业区")
    @ApiModelProperty(name = "taskAreaName",value = "所属作业区",dataType = "String")
    private String taskAreaName;
    @ExcelField("环境单元风险类型")
    @ApiModelProperty(name = "factoryTypeName",value = "环境单元风险类型",dataType = "String")
    private String factoryTypeName;
    @ExcelField(value = "管线基本信息",className = "com.cj.analysis.pojo.PipelineInfoView")
    @ApiModelProperty(name = "pipelineInfoView",value = "管线基本信息",dataType = "<<PipelineInfoView>>")
    private PipelineInfoView pipelineInfoView;

    @ExcelField(value = "评估结果",className = "com.cj.analysis.pojo.PipelineTargetView")
    @ApiModelProperty(name = "pipelineTargetView",value = "评估结果",dataType = "<<PipelineTargetView>>")
    private PipelineTargetView pipelineTargetView;

    @ExcelField("评定结果")
    @ApiModelProperty(name = "result",value = "评定结果",dataType = "String")
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

    public PipelineInfoView getPipelineInfoView() {
        return pipelineInfoView;
    }

    public void setPipelineInfoView(PipelineInfoView pipelineInfoView) {
        this.pipelineInfoView = pipelineInfoView;
    }

    public PipelineTargetView getPipelineTargetView() {
        return pipelineTargetView;
    }

    public void setPipelineTargetView(PipelineTargetView pipelineTargetView) {
        this.pipelineTargetView = pipelineTargetView;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
