package com.cj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "作业区信息表")
public class TaskArea  implements Serializable {
    /**
     * 作业区信息表
     */
    @ApiModelProperty(name = "taskAreaId",value = "ID",dataType = "Long")
    private Long taskAreaId;

    /**
     * 矿ID
     */
    @ApiModelProperty(name = "factoryId",value = "矿ID",dataType = "Long")
    private Long factoryId;

    /**
     * 作业区名称
     */
    @ApiModelProperty(name = "taskAreaName",value = "作业区名称",dataType = "String")
    private String taskAreaName;

    /**
     * 辖区等级（账号-辖区关联表用）
     */
    @ApiModelProperty(name = "popedomGrade",value = "辖区等级（账号-辖区关联表用）",dataType = "String")
    private String popedomGrade;

    /**
     * 作业区信息表
     * @return task_area_id 作业区信息表
     */
    public Long getTaskAreaId() {
        return taskAreaId;
    }

    /**
     * 作业区信息表
     * @param taskAreaId 作业区信息表
     */
    public void setTaskAreaId(Long taskAreaId) {
        this.taskAreaId = taskAreaId;
    }

    /**
     * 矿ID
     * @return factory_id 矿ID
     */
    public Long getFactoryId() {
        return factoryId;
    }

    /**
     * 矿ID
     * @param factoryId 矿ID
     */
    public void setFactoryId(Long factoryId) {
        this.factoryId = factoryId;
    }

    /**
     * 作业区名称
     * @return task_area_name 作业区名称
     */
    public String getTaskAreaName() {
        return taskAreaName;
    }

    /**
     * 作业区名称
     * @param taskAreaName 作业区名称
     */
    public void setTaskAreaName(String taskAreaName) {
        this.taskAreaName = taskAreaName == null ? null : taskAreaName.trim();
    }

    /**
     * 辖区等级（账号-辖区关联表用）
     * @return popedom_grade 辖区等级（账号-辖区关联表用）
     */
    public String getPopedomGrade() {
        return popedomGrade;
    }

    /**
     * 辖区等级（账号-辖区关联表用）
     * @param popedomGrade 辖区等级（账号-辖区关联表用）
     */
    public void setPopedomGrade(String popedomGrade) {
        this.popedomGrade = popedomGrade == null ? null : popedomGrade.trim();
    }
}