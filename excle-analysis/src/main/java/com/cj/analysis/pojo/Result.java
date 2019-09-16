package com.cj.analysis.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "统计图结果对象")
public class Result {

    @ApiModelProperty(name = "major",value = "重大",dataType = "int")
    Integer major;
    @ApiModelProperty(name = "larger",value = "较大",dataType = "int")
    Integer larger;
    @ApiModelProperty(name = "general",value = "一般",dataType = "int")
    Integer general;

}
