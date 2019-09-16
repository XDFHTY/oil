package com.cj.exclegrade.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "结果分析实体类")
public class VoEnvironmentStation {
    @ApiModelProperty(name = "stationId",value="stationId",dataType ="Long" )
    private Long stationId;
    @ApiModelProperty(name = "datetime",value = "时间",dataType = "String")
    private String datetime;
}
