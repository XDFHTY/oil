package com.cj.exclemeasures.domain;

import com.cj.common.entity.MeasuresLast;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "UpdateMeasuresByLastYearReq 接口请求信息")
public class UpdateMeasuresByLastYearReq {


    @ApiModelProperty(name = "year",value = "年份 ",dataType = "String")
    private String year;

    @ApiModelProperty(name = "stationId",value = "场站ID ",dataType = "long")
    private long stationId;

    @ApiModelProperty(name = "oldMeasuresLasts",value = "旧数据 ",dataType = "List<MeasuresLast>")
    private List<MeasuresLast> oldMeasuresLasts;

    @ApiModelProperty(name = "newMeasuresLasts",value = "新数据 ",dataType = "List<MeasuresLast>")
    private List<MeasuresLast> newMeasuresLasts;
}
