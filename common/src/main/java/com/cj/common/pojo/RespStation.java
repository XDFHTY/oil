package com.cj.common.pojo;

import com.cj.common.entity.Station;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 场站返回实体类
 * Created by XD on 2018/10/15.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "场站返回实体类")
public class RespStation extends Station {
    /**
     * 工艺分类
     */
    @ApiModelProperty(name = "factoryType",value = "工艺分类",dataType = "String")
    private String factoryType;
    /**
     * 工艺分类名称
     */
    @ApiModelProperty(name = "factoryTypeName",value = "工艺分类名称",dataType = "String")
    private String factoryTypeName;
}
