package com.cj.analysis.pojo;

import com.cj.analysis.annotation.ExcelField;

public class PipelineTargetView {
    @ExcelField("P")
    private String P;
    @ExcelField("C")
    private String C;

    public String getP() {
        return P;
    }

    public void setP(String p) {
        P = p;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }
}
