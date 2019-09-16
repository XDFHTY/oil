package com.cj.exclemeasures.domain;

import com.cj.common.entity.Measures;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "FindMeasuresByThisYearResp 接口返回信息")
public class FindMeasuresByThisYearResp {

    @ApiModelProperty(name = "measures",value = "本年度风险防控信息集合 ",dataType = "List")
    private List<Measures> measures;

    @ApiModelProperty(name = "update",value = "是否可编辑 ",dataType = "Boolean")
    private Boolean update;
}
