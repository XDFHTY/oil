package com.cj.exclematerial.pojo;

import com.cj.exclematerial.annotation.ExcelField;
import com.cj.exclematerial.annotation.ExcelSheet;

@ExcelSheet("消防队分布表")
public class BrigadeBo {
    /**
     * 消防队分布表
     */
    @ExcelField("编号")
    private Long brigadeId;

    /**
     * 队伍名称
     */
    @ExcelField("队伍名称")
    private String brigade;

    /**
     * 负责人姓名
     */
    @ExcelField("负责人姓名")
    private String brigadePerson;

    /**
     * 负责人办公电话
     */
    @ExcelField("负责人办公电话")
    private Long brigadePersonTelphone;

    /**
     * 负责人联系方式
     */
    @ExcelField("负责人联系方式")
    private Long brigadePersonPhone;


    /**
     * 消防队分布表
     * @return brigade_id 消防队分布表
     */
    public Long getBrigadeId() {
        return brigadeId;
    }

    /**
     * 消防队分布表
     * @param brigadeId 消防队分布表
     */
    public void setBrigadeId(Long brigadeId) {
        this.brigadeId = brigadeId;
    }

    /**
     * 队伍名称
     * @return brigade 队伍名称
     */
    public String getBrigade() {
        return brigade;
    }

    /**
     * 队伍名称
     * @param brigade 队伍名称
     */
    public void setBrigade(String brigade) {
        this.brigade = brigade == null ? null : brigade.trim();
    }

    /**
     * 负责人姓名
     * @return brigade_person 负责人姓名
     */
    public String getBrigadePerson() {
        return brigadePerson;
    }

    /**
     * 负责人姓名
     * @param brigadePerson 负责人姓名
     */
    public void setBrigadePerson(String brigadePerson) {
        this.brigadePerson = brigadePerson == null ? null : brigadePerson.trim();
    }

    /**
     * 负责人办公电话
     * @return brigade_person_telphone 负责人办公电话
     */
    public Long getBrigadePersonTelphone() {
        return brigadePersonTelphone;
    }

    /**
     * 负责人办公电话
     * @param brigadePersonTelphone 负责人办公电话
     */
    public void setBrigadePersonTelphone(Long brigadePersonTelphone) {
        this.brigadePersonTelphone = brigadePersonTelphone;
    }

    /**
     * 负责人联系方式
     * @return brigade_person_phone 负责人联系方式
     */
    public Long getBrigadePersonPhone() {
        return brigadePersonPhone;
    }

    /**
     * 负责人联系方式
     * @param brigadePersonPhone 负责人联系方式
     */
    public void setBrigadePersonPhone(Long brigadePersonPhone) {
        this.brigadePersonPhone = brigadePersonPhone;
    }

}