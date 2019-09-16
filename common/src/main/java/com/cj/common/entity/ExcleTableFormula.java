package com.cj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* Created by lw 2018/11/27
*/
@Data
@ApiModel(value = "excle_table_formula")
public class ExcleTableFormula {
    /**
     * excle_table表对应公式
     */
    @ApiModelProperty(name = "formulaId",value = "excle_table表对应公式",dataType = "java.lang.Long")
    private Long formulaId;

    /**
     * excle_table_id
     */
    @ApiModelProperty(name = "excleTableId",value = "excle_table_id",dataType = "java.lang.Long")
    private Long excleTableId;

    /**
     * 公式
     */
    @ApiModelProperty(name = "formula",value = "公式",dataType = "java.lang.String")
    private String formula;

    /**
     * 公式分类,1-计算公式，2-取值公式
     */
    @ApiModelProperty(name = "formulaType",value = "公式分类,1-计算公式，2-取值公式",dataType = "java.lang.String")
    private String formulaType;

    /**
     * 公式调用顺序
     */
    @ApiModelProperty(name = "formulaSort",value = "公式调用顺序",dataType = "java.lang.Integer")
    private Integer formulaSort;

    /**
     * 结果存放类型 1-列 2-坐标
     */
    @ApiModelProperty(name = "resultType",value = "结果存放类型 1-列 2-坐标",dataType = "java.lang.String")
    private String resultType;

    /**
     * 计算结果存放列
     */
    @ApiModelProperty(name = "sumCol",value = "计算结果存放列",dataType = "java.lang.Integer")
    private Integer sumCol;

    /**
     * 计算结果存放坐标，当result_type=2时使用此坐标
     */
    @ApiModelProperty(name = "sumIndex",value = "计算结果存放坐标，当result_type=2时使用此坐标",dataType = "java.lang.String")
    private String sumIndex;
}