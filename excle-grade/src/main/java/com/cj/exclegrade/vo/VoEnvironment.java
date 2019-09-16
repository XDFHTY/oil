package com.cj.exclegrade.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@ApiModel(value = "结果分析实体类")
@Data
public class VoEnvironment {
    @ApiModelProperty(name = "factoryId",value="factoryId",dataType ="Long" )
    private Long factoryId;
    @ApiModelProperty(name = "taskAreaId",value="taskAreaId",dataType ="Long" )
    private Long taskAreaId;
    @ApiModelProperty(name = "datetime",value = "时间",dataType = "String")
    private String datetime;
    @ApiModelProperty(name = "pageSize",value = "每页大小",dataType = "Integer")
    private Integer pageSize=10;
    @ApiModelProperty(name = "currentPage",value = "当前页",dataType = "Integer")
    private Integer currentPage=1;
    @ApiModelProperty(name = "currentIndex",value = "当前页",dataType = "Integer",hidden = true)
    private Integer currentIndex;
}
