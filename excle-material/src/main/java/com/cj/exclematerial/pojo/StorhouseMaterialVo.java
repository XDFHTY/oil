package com.cj.exclematerial.pojo;

import com.cj.common.entity.Storehouse;

import java.util.Date;

public class StorhouseMaterialVo {
    private Storehouse storehouse;
    private Integer materialNum;
    private Date year;

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public Storehouse getStorehouse() {
        return storehouse;
    }

    public void setStorehouse(Storehouse storehouse) {
        this.storehouse = storehouse;
    }

    public Integer getMaterialNum() {
        return materialNum;
    }

    public void setMaterialNum(Integer materialNum) {
        this.materialNum = materialNum;
    }
}
