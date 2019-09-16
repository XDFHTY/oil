package com.cj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "excle表格信息列表")
public class ExcleTableStructure  implements Serializable {
    /**
     * excle表结构信息
     */
    @ApiModelProperty(name = "excleTableStructureId",value = "id",dataType = "Long")
    private Long excleTableStructureId;

    /**
     * excle表格ID
     */
    @ApiModelProperty(name = "excleTableId",value = "excle表格ID",dataType = "Long")
    private Long excleTableId;

    /**
     * 开始行
     */
    @ApiModelProperty(name = "startRow",value = "开始行",dataType = "Integer")
    private Integer startRow;

    /**
     * 结束行
     */
    @ApiModelProperty(name = "endRow",value = "结束行",dataType = "Integer")
    private Integer endRow;

    /**
     * 开始列
     */
    @ApiModelProperty(name = "startCol",value = "开始列",dataType = "Integer")
    private Integer startCol;

    /**
     * 结束列
     */
    @ApiModelProperty(name = "endCol",value = "结束列",dataType = "Integer")
    private Integer endCol;

    /**
     * 单元格内容
     */
    @ApiModelProperty(name = "cellMsg",value = "单元格内容",dataType = "String")
    private String cellMsg;

    /**
     * 单元格高度(px)
     */
    @ApiModelProperty(name = "cellHeight",value = "单元格高度",dataType = "Integer")
    private Integer cellHeight;

    /**
     * 单元格宽度(px)
     */
    @ApiModelProperty(name = "cellWidth",value = "单元格宽度",dataType = "Integer")
    private Integer cellWidth;

    /**
     * 是否只读
     */
    @ApiModelProperty(name = "onlyRead",value = "是否只读",dataType = "String")
    private String onlyRead;

    /**
     * 是否表头（前端用，excle导出不用）
     */
    @ApiModelProperty(name = "tableHead",value = "是否表头（前端用，excle导出不用）",dataType = "String")
    private String tableHead;

    /**
     * true-只能用于计算，flase-默认为false
     */
    @ApiModelProperty(name = "hidden",value = "是否隐藏",dataType = "String")
    private String hidden;

    /**
     * 单元格分类，1-字符串，2-单选框，3-多选框，4-文件
     */
    @ApiModelProperty(name = "cellType",value = "单元格分类，1-字符串，2-单选框，3-多选框，4-文件",dataType = "String")
    private String cellType;

    /**
     * excle表结构信息
     * @return excle_table_structure_id excle表结构信息
     */
    public Long getExcleTableStructureId() {
        return excleTableStructureId;
    }

    /**
     * excle表结构信息
     * @param excleTableStructureId excle表结构信息
     */
    public void setExcleTableStructureId(Long excleTableStructureId) {
        this.excleTableStructureId = excleTableStructureId;
    }

    /**
     * excle表格ID
     * @return excle_table_id excle表格ID
     */
    public Long getExcleTableId() {
        return excleTableId;
    }

    /**
     * excle表格ID
     * @param excleTableId excle表格ID
     */
    public void setExcleTableId(Long excleTableId) {
        this.excleTableId = excleTableId;
    }

    /**
     * 开始行
     * @return start_row 开始行
     */
    public Integer getStartRow() {
        return startRow;
    }

    /**
     * 开始行
     * @param startRow 开始行
     */
    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    /**
     * 结束行
     * @return end_row 结束行
     */
    public Integer getEndRow() {
        return endRow;
    }

    /**
     * 结束行
     * @param endRow 结束行
     */
    public void setEndRow(Integer endRow) {
        this.endRow = endRow;
    }

    /**
     * 开始列
     * @return start_col 开始列
     */
    public Integer getStartCol() {
        return startCol;
    }

    /**
     * 开始列
     * @param startCol 开始列
     */
    public void setStartCol(Integer startCol) {
        this.startCol = startCol;
    }

    /**
     * 结束列
     * @return end_col 结束列
     */
    public Integer getEndCol() {
        return endCol;
    }

    /**
     * 结束列
     * @param endCol 结束列
     */
    public void setEndCol(Integer endCol) {
        this.endCol = endCol;
    }

    /**
     * 单元格内容
     * @return cell_msg 单元格内容
     */
    public String getCellMsg() {
        return cellMsg;
    }

    /**
     * 单元格内容
     * @param cellMsg 单元格内容
     */
    public void setCellMsg(String cellMsg) {
        this.cellMsg = cellMsg == null ? null : cellMsg.trim();
    }

    /**
     * 单元格高度(px)
     * @return cell_height 单元格高度(px)
     */
    public Integer getCellHeight() {
        return cellHeight;
    }

    /**
     * 单元格高度(px)
     * @param cellHeight 单元格高度(px)
     */
    public void setCellHeight(Integer cellHeight) {
        this.cellHeight = cellHeight;
    }

    /**
     * 单元格宽度(px)
     * @return cell_width 单元格宽度(px)
     */
    public Integer getCellWidth() {
        return cellWidth;
    }

    /**
     * 单元格宽度(px)
     * @param cellWidth 单元格宽度(px)
     */
    public void setCellWidth(Integer cellWidth) {
        this.cellWidth = cellWidth;
    }

    /**
     * 是否只读
     * @return only_read 是否只读
     */
    public String getOnlyRead() {
        return onlyRead;
    }

    /**
     * 是否只读
     * @param onlyRead 是否只读
     */
    public void setOnlyRead(String onlyRead) {
        this.onlyRead = onlyRead == null ? null : onlyRead.trim();
    }

    /**
     * 是否表头（前端用，excle导出不用）
     * @return table_head 是否表头（前端用，excle导出不用）
     */
    public String getTableHead() {
        return tableHead;
    }

    /**
     * 是否表头（前端用，excle导出不用）
     * @param tableHead 是否表头（前端用，excle导出不用）
     */
    public void setTableHead(String tableHead) {
        this.tableHead = tableHead == null ? null : tableHead.trim();
    }

    /**
     * 是否隐藏
     * @return hidden 是否隐藏
     */
    public String getHidden() {
        return hidden;
    }

    /**
     * 是否隐藏
     * @param hidden 是否隐藏
     */
    public void setHidden(String hidden) {
        this.hidden = hidden == null ? null : hidden.trim();
    }

    /**
     * 单元格分类，1-字符串，2-单选框，3-多选框，4-文件
     * @return cell_type 单元格分类，1-字符串，2-单选框，3-多选框，4-文件
     */
    public String getCellType() {
        return cellType;
    }

    /**
     * 单元格分类，1-字符串，2-单选框，3-多选框，4-文件
     * @param cellType 单元格分类，1-字符串，2-单选框，3-多选框，4-文件
     */
    public void setCellType(String cellType) {
        this.cellType = cellType == null ? null : cellType.trim();
    }
}