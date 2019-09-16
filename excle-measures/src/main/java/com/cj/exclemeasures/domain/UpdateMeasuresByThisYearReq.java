package com.cj.exclemeasures.domain;

import com.cj.common.entity.Measures;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "UpdateMeasuresByThisYearReq 接口请求信息")
public class UpdateMeasuresByThisYearReq {

    @ApiModelProperty(name = "year",value = "年份 ",dataType = "String")
    private String year;

    @ApiModelProperty(name = "stationId",value = "场站ID ",dataType = "long")
    private long stationId;

    @ApiModelProperty(name = "oldMeasures",value = "旧数据 ",dataType = "List<Measures>")
    private List<Measures> oldMeasures;

    @ApiModelProperty(name = "newMeasures",value = "新数据 ",dataType = "List<Measures>")
    private List<Measures> newMeasures;
}
