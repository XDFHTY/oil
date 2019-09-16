package com.cj.exclecheck.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 待审核 返回实体类
 * Created by XD on 2018/10/26.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespUnaudited implements Serializable {
    //场站id
    private Long stationId;

    //作业区id
    private Long taskAreaId;

    //提交时间
    private Date submitTime;

    private String factoryName;

    private String taskAreaName;

    private String stationName;

    private String factoryTypeName;

    private String year;


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
}
