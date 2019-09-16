package com.cj.exclegrade.vo;

import com.cj.exclepublic.domain.AddTableCellRecordsReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author 黄维
 * @date 2018/11/29 15:24
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "环境等级评定对象")
public class
EnvironmentGradeVo {
    /**
     * 环境等级评定
     */
    @ApiModelProperty(name = "environmentalGradeId",value = "id",dataType = "Long")
    private Long environmentalGradeId;
    /**
     * 敏感性描述
     */
    @ApiModelProperty(name = "resultDescribe",value = "敏感性描述",dataType = "String")
    private String resultDescribe;
    /**
     * 评定指标
     */
    @ApiModelProperty(name = "target",value = "评定指标(Q水，E水)",dataType = "Long")
    private String target;

    /**
     * 评定结果（较大，一般，重大）
     */
    @ApiModelProperty(name = "result",value = "评定结果（Q1,E1,W1）",dataType = "String")
    private String result;


    /**
     * 场站ID
     */
    @ApiModelProperty(name = "stationId",value = "场站ID",dataType = "Integer")
    private Integer stationId;

    /**
     * 时间
     */
    @ApiModelProperty(name = "datetime",value = "场站ID",dataType = "String")
    private String datetime;

    /**
     * 时间
     */
    @ApiModelProperty(name = "tableCellRecordsReqs",value = "",dataType = " List<AddTableCellRecordsReq>")
    private List<AddTableCellRecordsReq> tableCellRecordsReqs;
}
