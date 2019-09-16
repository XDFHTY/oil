package com.cj.exclecheck.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 审核结果返回实体类
 * Created by XD on 2018/10/26.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespExamineResult implements Serializable {

    private Long factoryId;

    private String factoryName;

    private Long taskAreaId;

    private String taskAreaName;

    private String factoryTypeName;

    /**
     * 场站id
     */
    private Long stationId;
    /**
     * 场站名称
     */
    private String stationName;

    /**
     * 提交时间
     */
    private Date submitTime;

    /**
     * 审核人姓名
     */
    private String fullName;

    /**
     * 审核意见
     */
    private String checkOpinion;

    /**
     * 审核结果
     */
    private String checkMsg;

    /**
     * 操作时间
     */
    private Date operatorTime;


}
