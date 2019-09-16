package com.cj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述:environment_grade表的实体类
 * @version
 * @author:  XD
 * @创建时间: 2018-10-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "环境等级评定对象")
public class EnvironmentGrade implements Serializable {
    /**
     * 环境等级评定
     */
    @ApiModelProperty(name = "environmentalGradeId",value = "id",dataType = "Long")
    private Long environmentalGradeId;

    /**
     * 评定指标
     */
    @ApiModelProperty(name = "target",value = "评定指标Id",dataType = "Long")
    private Long target;

    /**
     * 评定结果（较大，一般，重大）
     */
    @ApiModelProperty(name = "result",value = "评定结果（较大，一般，重大）",dataType = "String")
    private String result;

    /**
     * 指标描述（如果是得分就是分数，如果是描述就是描述）
     */
    @ApiModelProperty(name = "resultDescribe",value = "指标描述（如果是得分就是分数，如果是描述就是描述）",dataType = "String")
    private String resultDescribe;

    /**
     * 场站ID
     */
    @ApiModelProperty(name = "stationId",value = "场站ID",dataType = "Integer")
    private Integer stationId;

    /**
     * 评分时间
     */
    @ApiModelProperty(name = "gradetime",value = "评分时间",dataType = "Date")
    private Date gradetime;

    /**
     * 评定人姓名
     */
    @ApiModelProperty(name = "gradename",value = "评定人姓名",dataType = "String")
    private String gradename;

    public String getGradename() {
        return gradename;
    }

    public void setGradename(String gradename) {
        this.gradename = gradename;
    }

    /**
     * 环境等级评定
     * @return environmental_grade_id 环境等级评定
     */
    public Long getEnvironmentalGradeId() {
        return environmentalGradeId;
    }

    /**
     * 环境等级评定
     * @param environmentalGradeId 环境等级评定
     */
    public void setEnvironmentalGradeId(Long environmentalGradeId) {
        this.environmentalGradeId = environmentalGradeId;
    }

    /**
     * 评定指标
     * @return target 评定指标
     */
    public Long getTarget() {
        return target;
    }

    /**
     * 评定指标
     * @param target 评定指标
     */
    public void setTarget(Long target) {
        this.target = target;
    }

    /**
     * 评定结果（较大，一般，严重）
     * @return result 评定结果（较大，一般，严重）
     */
    public String getResult() {
        return result;
    }

    /**
     * 评定结果（较大，一般，严重）
     * @param result 评定结果（较大，一般，严重）
     */
    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    /**
     * 指标描述（如果是得分就是分数，如果是描述就是描述）
     * @return result_describe 指标描述（如果是得分就是分数，如果是描述就是描述）
     */
    public String getResultDescribe() {
        return resultDescribe;
    }

    /**
     * 指标描述（如果是得分就是分数，如果是描述就是描述）
     * @param resultDescribe 指标描述（如果是得分就是分数，如果是描述就是描述）
     */
    public void setResultDescribe(String resultDescribe) {
        this.resultDescribe = resultDescribe == null ? null : resultDescribe.trim();
    }

    /**
     * 场站ID
     * @return station_id 场站ID
     */
    public Integer getStationId() {
        return stationId;
    }

    /**
     * 场站ID
     * @param stationId 场站ID
     */
    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    /**
     * 评分时间
     * @return gradetime 评分时间
     */
    public Date getGradetime() {
        return gradetime;
    }

    /**
     * 评分时间
     * @param gradetime 评分时间
     */
    public void setGradetime(Date gradetime) {
        this.gradetime = gradetime;
    }
}