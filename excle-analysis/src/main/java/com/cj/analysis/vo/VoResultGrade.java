package com.cj.analysis.vo;

import lombok.Data;

/**
 * Created by XD on 2019/1/15.
 */
@Data
public class VoResultGrade {
    private String factoryName;
    private String taskAreaName;
    private String factoryTypeName;
    private String stationName;
    private Long stationId;
    private String qw;
    private String ew;
    private String mw;
    private String qg;
    private String eg;
    private String mg;
    private String result;
    private String tableName;

    //管线起点
    private String startStation;
    //管线终点
    private String endStation;

    private String p;
    private String c;
}
