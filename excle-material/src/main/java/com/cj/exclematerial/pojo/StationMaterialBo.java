package com.cj.exclematerial.pojo;


import com.cj.exclematerial.annotation.ExcelField;
import com.cj.exclematerial.annotation.ExcelSheet;
import lombok.Data;

@Data
@ExcelSheet(value = "应急物资调查表")
public class StationMaterialBo {
    @ExcelField("序号")
    private Integer no;
    @ExcelField("应急物资名称")
    private String materialName ;
    @ExcelField("技术要求或功能要求")
    private String technicalRequirement;
    @ExcelField("配备要求/单位")
    private String materialUtil;
    @ExcelField("物资数量")
    private String materNum;

}
