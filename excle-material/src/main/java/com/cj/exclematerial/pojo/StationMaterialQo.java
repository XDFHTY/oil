package com.cj.exclematerial.pojo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author 黄维
 * @date 2018/12/10 12:21
 **/
@Data
@ApiModel("场站物资查询对象")
public class StationMaterialQo {
    private String datetime;
    private Long stationId;
}
