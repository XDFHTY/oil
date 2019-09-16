package com.cj.analysis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@ApiModel(value = "结果分析实体类")
@Data
public class VoEnvironment {
    @ApiModelProperty(value="taskAreaId数组",hidden=true)
    private Long[] factoryIds;
    private List<Long> factoryIdList;
    @ApiModelProperty(value="taskAreaId数组",hidden=true)
    private Long[] taskAreaIds;
    private List<Long> taskAreaIdList;
    @ApiModelProperty(name = "datetime",value = "时间",dataType = "String")
    private String datetime;
    @ApiModelProperty(name = "pageSize",value = "每页大小",dataType = "Integer")
    private Integer pageSize=10;
    @ApiModelProperty(name = "currentPage",value = "当前页",dataType = "Integer")
    private Integer currentPage=1;
    @ApiModelProperty(name = "shape",value = "点型，线型（1表示点型，2表示线型）",dataType = "Integer")
    private Integer shape=1;

//    @ApiModelProperty(name = "isIllegality",value = "是否违法",dataType = "Booblen")
//    private boolean isIllegality;

    private String target;

    private String QW;
    private String EW;
    private String MW;

    private String QG;
    private String EG;
    private String MG;


    private String PMIN;
    private String PMAX;

    private String CMIN;
    private String CMAX;

}
