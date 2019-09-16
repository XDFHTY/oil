package com.cj.exclepublic.domain;

import lombok.Data;

@Data
public class FindStruReq {

    private long stationId;

    private String tableName;

    private String year;
}
