package com.cj.exclematerial.pojo;

import com.cj.exclematerial.annotation.ExcelField;
import com.cj.exclematerial.annotation.ExcelSheet;

@ExcelSheet("检修队分布情况表")
public class OverhaulBo {
    /**
     * 检修队分布表
     */
    @ExcelField("序号")
    private Long overhaulId;

    /**
     * 队伍名称
     */
    @ExcelField("队伍名称")
    private String overhaul;

    /**
     * 负责人姓名
     */
    @ExcelField("负责人姓名")
    private String overhaulPerson;

    /**
     * 负责人办公电话
     */
    @ExcelField("负责人办公电话")
    private Long overhaulPersonTelphone;

    /**
     * 负责人联系方式
     */
    @ExcelField("负责人联系方式")
    private Long overhaulPersonPhone;


    /**
     * 检修队分布表
     * @return overhaul_id 检修队分布表
     */
    public Long getOverhaulId() {
        return overhaulId;
    }

    /**
     * 检修队分布表
     * @param overhaulId 检修队分布表
     */
    public void setOverhaulId(Long overhaulId) {
        this.overhaulId = overhaulId;
    }

    /**
     * 队伍名称
     * @return overhaul 队伍名称
     */
    public String getOverhaul() {
        return overhaul;
    }

    /**
     * 队伍名称
     * @param overhaul 队伍名称
     */
    public void setOverhaul(String overhaul) {
        this.overhaul = overhaul == null ? null : overhaul.trim();
    }

    /**
     * 负责人姓名
     * @return overhaul_person 负责人姓名
     */
    public String getOverhaulPerson() {
        return overhaulPerson;
    }

    /**
     * 负责人姓名
     * @param overhaulPerson 负责人姓名
     */
    public void setOverhaulPerson(String overhaulPerson) {
        this.overhaulPerson = overhaulPerson == null ? null : overhaulPerson.trim();
    }

    /**
     * 负责人办公电话
     * @return overhaul_person_telphone 负责人办公电话
     */
    public Long getOverhaulPersonTelphone() {
        return overhaulPersonTelphone;
    }

    /**
     * 负责人办公电话
     * @param overhaulPersonTelphone 负责人办公电话
     */
    public void setOverhaulPersonTelphone(Long overhaulPersonTelphone) {
        this.overhaulPersonTelphone = overhaulPersonTelphone;
    }

    /**
     * 负责人联系方式
     * @return overhaul_person_phone 负责人联系方式
     */
    public Long getOverhaulPersonPhone() {
        return overhaulPersonPhone;
    }

    /**
     * 负责人联系方式
     * @param overhaulPersonPhone 负责人联系方式
     */
    public void setOverhaulPersonPhone(Long overhaulPersonPhone) {
        this.overhaulPersonPhone = overhaulPersonPhone;
    }

}