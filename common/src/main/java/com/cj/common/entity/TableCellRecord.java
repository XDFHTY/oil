package com.cj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "单元格信息记录")
public class TableCellRecord  implements Serializable {
    /**
     * 单元格信息记录
     */
    @ApiModelProperty(name = "tableCellRecordId",value = "ID",dataType = "Long")
    private long tableCellRecordId;

    /**
     * 新增表信息记录ID
     */
    @ApiModelProperty(name = "tableRecordId",value = "新增表信息记录ID",dataType = "Long")
    private long tableRecordId;

    /**
     * 表格结构ID(单元格的唯一标志)
     */
    @ApiModelProperty(name = "excleTableStructureId",value = "表格结构ID(单元格的唯一标志)",dataType = "Long")
    private long excleTableStructureId;

    /**
     * 单元格内容
     */
    @ApiModelProperty(name = "cellMsg",value = "单元格内容",dataType = "String")
    private String cellMsg;

    /**
     * 创建人ID
     */
    @ApiModelProperty(name = "founderId",value = "创建人ID",dataType = "Long")
    private Long founderId;

    /**
     * 创建时间
     */
    @ApiModelProperty(name = "createTime",value = "创建时间",dataType = "Date")
    private Date createTime;

    /**
     * 单元格内容状态，0-已停止使用，1-正在使用
     */
    @ApiModelProperty(name = "cellState",value = "单元格内容状态，0-已停止使用，1-正在使用",dataType = "String")
    private String cellState;
}