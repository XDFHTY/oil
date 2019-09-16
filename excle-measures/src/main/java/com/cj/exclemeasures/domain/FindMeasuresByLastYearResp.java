package com.cj.exclemeasures.domain;


import com.cj.common.entity.MeasuresLast;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "FindMeasuresByLastYearResp 接口返回信息")
public class FindMeasuresByLastYearResp {

    @ApiModelProperty(name = "measuresLasts",value = "上年度风险防控信息集合 ",dataType = "List")
    private List<MeasuresLast> measuresLasts;

    @ApiModelProperty(name = "update",value = "是否可编辑 ",dataType = "Boolean")
    private Boolean update;
}
