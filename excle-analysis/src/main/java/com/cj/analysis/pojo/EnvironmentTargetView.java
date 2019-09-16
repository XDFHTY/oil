package com.cj.analysis.pojo;

import com.cj.analysis.annotation.ExcelField;

public class EnvironmentTargetView {
    @ExcelField("Q")
    private String Q;
    @ExcelField("E")
    private String E;
    @ExcelField("M")
    private String M;

    public String getQ() {
        return Q;
    }

    public void setQ(String q) {
        Q = q;
    }

    public String getE() {
        return E;
    }

    public void setE(String e) {
        E = e;
    }

    public String getM() {
        return M;
    }

    public void setM(String m) {
        M = m;
    }
}
