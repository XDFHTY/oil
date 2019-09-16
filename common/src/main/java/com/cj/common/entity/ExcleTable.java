package com.cj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "excle表格信息列表")
public class ExcleTable  implements Serializable {
    /**
     * excle表格信息列表
     */
    @ApiModelProperty(name = "excleTableId",value = "id",dataType = "Long")
    private Long excleTableId;

    /**
     * exlce表格名称
     */
    @ApiModelProperty(name = "excleTableId",value = "exlce表格名称",dataType = "Long")
    private String excleTableName;

    /**
     * excle表格填写顺序
     */
    @ApiModelProperty(name = "excleSort",value = "excle表格填写顺序",dataType = "Integer")
    private Integer excleSort;

    /**
     * excle表格工艺数量
     */
    @ApiModelProperty(name = "factoryTypeNum",value = "excle表格工艺数量",dataType = "Integer")
    private Integer factoryTypeNum;
}