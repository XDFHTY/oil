package com.cj.exclematerial.pojo;

import lombok.Data;

/**
 * @author 黄维
 * @date 2018/12/11 10:45
 **/
@Data
public class StationMaterialEditVo {
    private Long stationId;
    private String datetime;
    private Integer materialNum;
    private String materialUtil;
    private String materialName;
    private String materialType;

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Integer getMaterialNum() {
        return materialNum;
    }

    public void setMaterialNum(Integer materialNum) {
        this.materialNum = materialNum;
    }

    public String getMaterialUtil() {
        return materialUtil;
    }

    public void setMaterialUtil(String materialUtil) {
        this.materialUtil = materialUtil;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }
}
