package com.cj.organization.entity;

import com.cj.common.utils.excle.IsNeeded;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 中心站/场站/联系人 返回实体类
 * Created by XD on 2018/10/12.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespStation implements Serializable {
    /**
     * 厂、矿基本信息表
     */
    @ApiModelProperty(name = "factoryId",value = "气矿ID",dataType = "Long")
    private Long factoryId;
    /**
     * 厂、矿名称
     */
    @ApiModelProperty(name = "factoryName",value = "气矿名称",dataType = "String")
    private String factoryName;
    /**
     * 作业区信息表
     */
    @ApiModelProperty(name = "taskAreaId",value = "作业区id",dataType = "Long")
    private Long taskAreaId;
    /**
     * 作业区名称
     */
    @ApiModelProperty(name = "taskAreaName",value = "作业区名称",dataType = "String")
    private String taskAreaName;

    /**
     * 场站id
     */
    @ApiModelProperty(name = "stationId",value = "场站id",dataType = "Long")
    private Long stationId;
    /**
     * 场站名称
     */
    @ApiModelProperty(name = "stationName",value = "场站名称",dataType = "String")
    private String stationName;
    /**
     * adminId
     */
    @ApiModelProperty(name = "adminId",value = "管理员id",dataType = "Long")
    private Long adminId;

    /**
     * 账号
     */
    @ApiModelProperty(name = "adminName",value = "管理员账号",dataType = "String")
    private String adminName;
    /**
     * 姓名
     */
    @ApiModelProperty(name = "fullName",value = "管理员姓名",dataType = "String")
    private String fullName;

}
