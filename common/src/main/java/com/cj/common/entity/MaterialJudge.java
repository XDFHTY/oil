package com.cj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "判定物资是否在某表中必须出现")
public class MaterialJudge  implements Serializable {
    /**
     * 判定物资是否在某表中必须出现
     */
    @ApiModelProperty(name = "materialUtil",value = "物资的单位",dataType = "String")
    private Long materialJudgeId;

    /**
     * 物资ID
     */
    @ApiModelProperty(name = "materialId",value = "物资ID",dataType = "Long")
    private Long materialId;

    /**
     * 表示该物资是某一个表的必须字段(0:不是必须字段,1:是资源统计表的必须字段，2:物资调查表的必须字段)
     */
    @ApiModelProperty(name = "flag",value = "表示该物资是某一个表的必须字段(0:不是必须字段,1:是资源统计表的必须字段，2:物资调查表的必须字段)",dataType = "String")
    private String flag;

     /**
     * 判定物资是否在某表中必须出现
     * @return material_judge_id 判定物资是否在某表中必须出现
     */
    public Long getMaterialJudgeId() {
        return materialJudgeId;
    }

    /**
     * 判定物资是否在某表中必须出现
     * @param materialJudgeId 判定物资是否在某表中必须出现
     */
    public void setMaterialJudgeId(Long materialJudgeId) {
        this.materialJudgeId = materialJudgeId;
    }

    /**
     * 物资ID
     * @return material_id 物资ID
     */
    public Long getMaterialId() {
        return materialId;
    }

    /**
     * 物资ID
     * @param materialId 物资ID
     */
    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    /**
     * 表示该物资是某一个表的必须字段(0:不是必须字段,1:是资源统计表的必须字段，2:物资调查表的必须字段)
     * @return flag 表示该物资是某一个表的必须字段(0:不是必须字段,1:是资源统计表的必须字段，2:物资调查表的必须字段)
     */
    public String getFlag() {
        return flag;
    }

    /**
     * 表示该物资是某一个表的必须字段(0:不是必须字段,1:是资源统计表的必须字段，2:物资调查表的必须字段)
     * @param flag 表示该物资是某一个表的必须字段(0:不是必须字段,1:是资源统计表的必须字段，2:物资调查表的必须字段)
     */
    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }
}