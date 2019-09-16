package com.cj.common.pojo;

import com.cj.common.entity.Station;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "场站物资对象")
public class StationMaterialVo {
    @ApiModelProperty(name = "materialId",value = "Id",dataType = "Long")
    private Long materialId;

    /**
     * 物资名字
     */
    @ApiModelProperty(name = "materialId",value = "物资名字",dataType = "String")
    private String materialName;

    /**
     * 物资的单位
     */
    @ApiModelProperty(name = "materialUtil",value = "物资的单位",dataType = "String")
    private String materialUtil;

    /**
     * w物资数量
     */
    @ApiModelProperty(name = "materialNum",value = "w物资数量",dataType = "Integer")
    private Integer materialNum;

    /**
     * 场站
     */
    @ApiModelProperty(name = "station",value = "场站",dataType = "com.cj.common.entity.Station")
    private Station station;

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialUtil() {
        return materialUtil;
    }

    public void setMaterialUtil(String materialUtil) {
        this.materialUtil = materialUtil;
    }

    public Integer getMaterialNum() {
        return materialNum;
    }

    public void setMaterialNum(Integer materialNum) {
        this.materialNum = materialNum;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }
}
