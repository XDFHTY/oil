package com.cj.common.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "储物库物资信息")
public class StorhouseMaterialVo {
    @ApiModelProperty(name = "storehouseMaterialId",value = "storehouseMaterialId",dataType = "Long")
    private Long storehouseMaterialId;
    @ApiModelProperty(name = "storehouseId",value = "storehouseId",dataType = "Long")
    private Long storehouseId;
    @ApiModelProperty(name = "storehouseName",value = "storehouseName",dataType = "String")
    private String storehouseName;
    @ApiModelProperty(name = "materialNum",value = "物资数量",dataType = "Integer")
    private Integer materialNum;
    @ApiModelProperty(name = "year",value = "日期",dataType = "Date")
    private Date year;

    public Long getStorehouseId() {
        return storehouseId;
    }

    public void setStorehouseId(Long storehouseId) {
        this.storehouseId = storehouseId;
    }

    public String getStorehouseName() {
        return storehouseName;
    }

    public void setStorehouseName(String storehouseName) {
        this.storehouseName = storehouseName;
    }

    public Long getStorehouseMaterialId() {
        return storehouseMaterialId;
    }

    public void setStorehouseMaterialId(Long storehouseMaterialId) {
        this.storehouseMaterialId = storehouseMaterialId;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public Integer getMaterialNum() {
        return materialNum;
    }

    public void setMaterialNum(Integer materialNum) {
        this.materialNum = materialNum;
    }
}
