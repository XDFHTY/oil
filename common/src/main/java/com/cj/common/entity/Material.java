package com.cj.common.entity;

import com.cj.common.pojo.StorhouseMaterialVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "物资表")
@Data
public class Material  implements Serializable {
    /**
     * 物资表
     */
    @ApiModelProperty(name = "materialId",value = "物资表",dataType = "Long")
    private Long materialId;

    /**
     * 物资名字
     */
    @ApiModelProperty(name = "materialName",value = "物资名字",dataType = "String")
    private String materialName;

    /**
     * 物资的单位
     */
    @ApiModelProperty(name = "materialUtil",value = "物资的单位",dataType = "String")
    private String materialUtil;

    /**
     * 物资所属 1-气矿 2-场站
     */
    @ApiModelProperty(name = "materialType",value = "物资所属 1-气矿 2-场站",dataType = "String")
    private String materialType;

    private List<StorhouseMaterialVo> storhouseMaterialVoList;

    private Integer total;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<StorhouseMaterialVo> getStorhouseMaterialVoList() {
        return storhouseMaterialVoList;
    }

    public void setStorhouseMaterialVoList(List<StorhouseMaterialVo> storhouseMaterialVoList) {
        this.storhouseMaterialVoList = storhouseMaterialVoList;
    }

    /**
     * 物资表
     * @return material_id 物资表
     */
    public Long getMaterialId() {
        return materialId;
    }

    /**
     * 物资表
     * @param materialId 物资表
     */
    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    /**
     * 物资名字
     * @return material_name 物资名字
     */
    public String getMaterialName() {
        return materialName;
    }

    /**
     * 物资名字
     * @param materialName 物资名字
     */
    public void setMaterialName(String materialName) {
        this.materialName = materialName == null ? null : materialName.trim();
    }

    /**
     * 物资的单位
     * @return material_util 物资的单位
     */
    public String getMaterialUtil() {
        return materialUtil;
    }

    /**
     * 物资的单位
     * @param materialUtil 物资的单位
     */
    public void setMaterialUtil(String materialUtil) {
        this.materialUtil = materialUtil == null ? null : materialUtil.trim();
    }
}