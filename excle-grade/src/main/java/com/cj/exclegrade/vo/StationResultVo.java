package com.cj.exclegrade.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author 黄维
 * @date 2018/11/23 18:24
 **/
@ApiModel("计算结果实体类")
@Data
public class StationResultVo {
//    工艺类型
    String stationType;
//    水环境Q
    String QW;
//水环境E
    String EW;
//水环境M
    String MW;
//    大气环境Q
    String QG;
//    大气环境E
    String EG;
//    大气环境M
    String MG;
//    P值
    Integer P;
//    C值
    Integer C;
//    是否违法
    Boolean danger;
}
