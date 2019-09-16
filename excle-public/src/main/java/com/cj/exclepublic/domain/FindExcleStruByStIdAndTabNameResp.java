package com.cj.exclepublic.domain;

import com.cj.common.entity.ExcleTableFormula;
import com.cj.common.entity.ExcleTableStructure;
import com.cj.common.entity.TableCellRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "findExcleStruByStIdAndTabName 接口返回参数")
public class FindExcleStruByStIdAndTabNameResp {
    @ApiModelProperty(name = "stationId",value = "场站id（台账模块）",dataType = "List")
    private Long stationId;

    @ApiModelProperty(name = "excleTableStructures",value = "表结构信息",dataType = "List")
    private List<ExcleTableStructure> excleTableStructures;

    @ApiModelProperty(name = "tableCellRecords",value = "表内容信息",dataType = "List")
    private List<TableCellRecord> tableCellRecords;

    @ApiModelProperty(name = "tableCellRecords",value = "上年表内容信息",dataType = "List")
    private List<TableCellRecord> lastTableCellRecords;

    @ApiModelProperty(name = "excleTableFormula",value = "表格内公式",dataType = "ExcleTableFormula")
    private List<ExcleTableFormula> excleTableFormulas;

    @ApiModelProperty(name = "update",value = "表格是否允许编辑",dataType = "Boolean")
    private Boolean update;
}
