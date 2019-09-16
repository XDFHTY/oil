package com.cj.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * 描述Excle的坐标信息
 */
@Data
@ApiModel(value = "描述Excle的坐标信息")
public class ExclePoint  implements Serializable {
    @ApiModelProperty(name = "startRow",value = "",dataType = "int")
    private int startRow;
    @ApiModelProperty(name = "endRow",value = "",dataType = "int")
    private int endRow;
    @ApiModelProperty(name = "startCol",value = "",dataType = "int")
    private int startCol;
    @ApiModelProperty(name = "endCol",value = "",dataType = "int")
    private int endCol;

    public ExclePoint(int startRow, int endRow, int startCol, int endCol) {
        this.startRow = startRow;
        this.endRow = endRow;
        this.startCol = startCol;
        this.endCol = endCol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExclePoint that = (ExclePoint) o;
        return startRow == that.startRow &&
                endRow == that.endRow &&
                startCol == that.startCol &&
                endCol == that.endCol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startRow, endRow, startCol, endCol);
    }
}
