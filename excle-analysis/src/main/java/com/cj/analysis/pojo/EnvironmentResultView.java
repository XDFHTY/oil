package com.cj.analysis.pojo;

import com.cj.analysis.annotation.ExcelField;

public class EnvironmentResultView {

    @ExcelField(value = "水环境风险",className = "com.cj.analysis.pojo.EnvironmentTargetView")
    private EnvironmentTargetView gass;
    @ExcelField(value = "大气环境风险",className = "com.cj.analysis.pojo.EnvironmentTargetView")
    private EnvironmentTargetView water;


    public EnvironmentTargetView getGass() {
        return gass;
    }

    public void setGass(EnvironmentTargetView gass) {
        this.gass = gass;
    }

    public EnvironmentTargetView getWater() {
        return water;
    }

    public void setWater(EnvironmentTargetView water) {
        this.water = water;
    }
}
