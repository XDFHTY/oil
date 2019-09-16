package com.cj.exclematerial.pojo;

import com.cj.exclematerial.annotation.ExcelField;
import com.cj.exclematerial.annotation.ExcelSheet;
@ExcelSheet("医疗机构分布表")
public class HospitalBo {
    /**
     * 医疗机构表
     */
    @ExcelField("序号")
    private Long hospitalId;

    /**
     * 机构名称
     */
    @ExcelField("机构名称")
    private String hospitalName;

    /**
     * 机构地址
     */
    @ExcelField("医疗机构地址")
    private String hospitalAddress;

    /**
     * 机构等级
     */
    @ExcelField("医疗机构地址")
    private String hospitalLevel;

    /**
     * 是否配备高压氧仓(1是，0否)
     */
    @ExcelField("是否配备高压氧仓")
    private String oxygenChamber;


    /**
     * 医疗机构表
     * @return hospital_id 医疗机构表
     */
    public Long getHospitalId() {
        return hospitalId;
    }

    /**
     * 医疗机构表
     * @param hospitalId 医疗机构表
     */
    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    /**
     * 机构名称
     * @return hospital_name 机构名称
     */
    public String getHospitalName() {
        return hospitalName;
    }

    /**
     * 机构名称
     * @param hospitalName 机构名称
     */
    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName == null ? null : hospitalName.trim();
    }

    /**
     * 机构地址
     * @return hospital_address 机构地址
     */
    public String getHospitalAddress() {
        return hospitalAddress;
    }

    /**
     * 机构地址
     * @param hospitalAddress 机构地址
     */
    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress == null ? null : hospitalAddress.trim();
    }

    /**
     * 机构等级
     * @return hospital_level 机构等级
     */
    public String getHospitalLevel() {
        return hospitalLevel;
    }

    /**
     * 机构等级
     * @param hospitalLevel 机构等级
     */
    public void setHospitalLevel(String hospitalLevel) {
        this.hospitalLevel = hospitalLevel == null ? null : hospitalLevel.trim();
    }

    /**
     * 是否配备高压氧仓(1是，0否)
     * @return oxygen_chamber 是否配备高压氧仓(1是，0否)
     */
    public String getOxygenChamber() {
        return oxygenChamber;
    }

    /**
     * 是否配备高压氧仓(1是，0否)
     * @param oxygenChamber 是否配备高压氧仓(1是，0否)
     */
    public void setOxygenChamber(String oxygenChamber) {
        this.oxygenChamber = oxygenChamber == null ? null : oxygenChamber.trim();
    }
}