package com.cj.exclepublic.domain;

import com.cj.common.entity.ExcleTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "FindExcleSort 接口返回参数")
public class FindExcleSortResp {


    @ApiModelProperty(name = "excleTablesBasic",value = "基础模块填表顺序",dataType = "List")
    private List<ExcleTable> excleTablesBasic;

    @ApiModelProperty(name = "excleTablesGrade",value = "风险等级模块填表顺序",dataType = "List")
    private List<ExcleTable> excleTablesGrade;
}
