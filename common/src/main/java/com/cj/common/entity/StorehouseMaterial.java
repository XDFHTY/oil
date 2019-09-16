package com.cj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "储备库表")
public class StorehouseMaterial  implements Serializable {
    /**
     * 储备库物资关系表
     */
    @ApiModelProperty(name = "storehouseMaterialId",value = "ID",dataType = "Long")
    private Long storehouseMaterialId;

    /**
     * 储备库ID
     */
    @ApiModelProperty(name = "storehouseId",value = "储备库ID",dataType = "Long")
    private Long storehouseId;

    /**
     * 物资ID
     */
    @ApiModelProperty(name = "materialId",value = "物资ID",dataType = "Long")
    private Long materialId;

    /**
     * 物资数量
     */
    @ApiModelProperty(name = "materialNum",value = "物资ID",dataType = "Integer")
    private Integer materialNum;

    /**
     * 物资-物资库关系表填表时间
     */
    @ApiModelProperty(name = "year",value = "物资-物资库关系表填表时间",dataType = "Date")
    private Date year;

    /**
     * 储备库物资关系表
     * @return storehouse_material_id 储备库物资关系表
     */
    public Long getStorehouseMaterialId() {
        return storehouseMaterialId;
    }

    /**
     * 储备库物资关系表
     * @param storehouseMaterialId 储备库物资关系表
     */
    public void setStorehouseMaterialId(Long storehouseMaterialId) {
        this.storehouseMaterialId = storehouseMaterialId;
    }

    /**
     * 储备库ID
     * @return storehouse_id 储备库ID
     */
    public Long getStorehouseId() {
        return storehouseId;
    }

    /**
     * 储备库ID
     * @param storehouseId 储备库ID
     */
    public void setStorehouseId(Long storehouseId) {
        this.storehouseId = storehouseId;
    }

    /**
     * 物资ID
     * @return material_id 物资ID
     */
    public Long getMaterialId() {
        return materialId;
    }

    /**
     * 物资ID
     * @param materialId 物资ID
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
     * 物资-物资库关系表填表时间
     * @return year 物资-物资库关系表填表时间
     */
    public Date getYear() {
        return year;
    }

    /**
     * 物资-物资库关系表填表时间
     * @param year 物资-物资库关系表填表时间
     */
    public void setYear(Date year) {
        this.year = year;
    }
}