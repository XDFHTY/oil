package com.cj.exclematerial.pojo;

import com.cj.exclematerial.annotation.ExcelField;
import com.cj.exclematerial.annotation.ExcelSheet;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ExcelSheet("储物库物资统计表")
@ApiModel(value = "储物库物资统计对象")
public class StorehouseMaterialBo {
    /**
     * 物资名字
     */
    @ExcelField("物资名称")
    @ApiModelProperty(name = "materialName",value = "物资名称",dataType = "String")
    private String materialName;

    /**
     * 物资的单位
     */
    @ExcelField("单位")
    @ApiModelProperty(name = "materialUtil",value = "单位",dataType = "String")
    private String materialUtil;
    /**
     * 需要扩展的列级列数据
     */
    @ApiModelProperty(name = "materialUtil",value = "单位",dataType = "String")
    List<ListBo> listBos;

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialUtil() {
        return materialUtil;
    }

    public void setMaterialUtil(String materialUtil) {
        this.materialUtil = materialUtil;
    }

    public List<ListBo> getListBos() {
        return listBos;
    }

    public void setListBos(List<ListBo> listBos) {
        this.listBos = listBos;
    }
}
