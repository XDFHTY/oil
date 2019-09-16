package com.cj.common.entity;

import io.swagger.annotations.ApiModel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "场站物资统计表")
public class StationMaterial  implements Serializable {
    /**
     * 场站物资统计表
     */
    private Long stationMaterialId;

    /**
     * 场站信息
     */
    private Long stationId;

    /**
     * 物资信息
     */
    private Long materialId;

    /**
     * 物资数量
     */
    private Integer materialNum;

    /**
     * 年份
     */
    private Date year;

    /**
     * 场站物资统计表
     * @return station_material_id 场站物资统计表
     */
    public Long getStationMaterialId() {
        return stationMaterialId;
    }

    /**
     * 场站物资统计表
     * @param stationMaterialId 场站物资统计表
     */
    public void setStationMaterialId(Long stationMaterialId) {
        this.stationMaterialId = stationMaterialId;
    }

    /**
     * 场站信息
     * @return station_id 场站信息
     */
    public Long getStationId() {
        return stationId;
    }

    /**
     * 场站信息
     * @param stationId 场站信息
     */
    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    /**
     * 物资信息
     * @return material_id 物资信息
     */
    public Long getMaterialId() {
        return materialId;
    }

    /**
     * 物资信息
     * @param materialId 物资信息
     */
    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    /**
     * 物资数量
     * @return material_num 物资数量
     */
    public Integer getMaterialNum() {
        return materialNum;
    }

    /**
     * 物资数量
     * @param materialNum 物资数量
     */
    public void setMaterialNum(Integer materialNum) {
        this.materialNum = materialNum;
    }

    /**
     * 年份
     * @return year 年份
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getYear() {
        return year;
    }

    /**
     * 年份
     * @param year 年份
     */
    public void setYear(Date year) {
        this.year = year;
    }
}