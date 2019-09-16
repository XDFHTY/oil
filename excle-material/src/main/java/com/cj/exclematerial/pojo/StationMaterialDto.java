package com.cj.exclematerial.pojo;

import lombok.Data;

/**
 * @author 黄维
 * @date 2018/12/11 13:42
 **/
@Data
public class StationMaterialDto {
    private String materialName;
    private String materialUtil;
    private Integer materialNum;
    private Long stationId;
    private Long stationMaterialId;

    public void setStationMaterialId(Long stationMaterialId) {
        this.stationMaterialId = stationMaterialId;
        if (this.materialNum==null){
            this.materialNum = 0;
        }
    }
}
