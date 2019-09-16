package com.cj.exclegrade.pojo;


import com.cj.exclegrade.annotation.ExcelField;
import com.cj.exclegrade.annotation.ExcelSheet;

/**
 * 管线结果集汇总
 */
@ExcelSheet("管线结果汇总表")
public class FactoryEnvironmentPipelineView {
    @ExcelField("所属单位")
    private String factoryName;
    @ExcelField("所属作业区")
    private String taskAreaName;
    @ExcelField("环境单元风险类型")
    private String factoryTypeName;
    @ExcelField(value = "管线基本信息",className = "com.cj.analysis.pojo.PipelineInfoView")
    private PipelineInfoView pipelineInfoView;

    @ExcelField(value = "评估结果",className = "com.cj.analysis.pojo.PipelineTargetView")
    private PipelineTargetView pipelineTargetView;

    @ExcelField("评定结果")
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
