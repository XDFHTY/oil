package com.cj.exclepublic.domain;


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
@ApiModel(value = "UpdateTableCellRecords 接口请求参数")
public class UpdateTableCellRecordsReq {


    @ApiModelProperty(name = "stationId",value = "场站ID",dataType = "long")
    private long stationId;

    @ApiModelProperty(name = "tableName",value = "表格名称",dataType = "String")
    private String tableName;

    @ApiModelProperty(name = "year",value = "年份",dataType = "String")
    private String year;


    @ApiModelProperty(name = "tableCellRecords",value = "表格新增内容集合",dataType = "StListring")
    private List<TableCellRecord> tableCellRecords;
}
