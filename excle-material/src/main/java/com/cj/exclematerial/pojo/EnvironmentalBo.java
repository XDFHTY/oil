package com.cj.exclematerial.pojo;

import com.cj.exclematerial.annotation.ExcelField;
import com.cj.exclematerial.annotation.ExcelSheet;

@ExcelSheet("环境机构分布表")
public class EnvironmentalBo {
    /**
     * 环境监测机构信息表
     */
    @ExcelField("序号")
    private Long environmentalId;

    /**
     * 机构名称
     */
    private String environmentalName;

    /**
     * 服务对象
     */
    @ExcelField("服务对象")
    private String environmentalServer;

    /**
     * 机构地址
     */
    @ExcelField("机构地址")
    private String environmentalAddress;

    /**
     * 联系人
     */
    @ExcelField("联系人")
    private String environmentalLinkman;

    /**
     * 联系人电话
     */
    @ExcelField("联系人电话")
    private Long environmentalLinkmanPhone;

    /**
     * 环境监测机构信息表
     * @return environmental_id 环境监测机构信息表
     */
    public Long getEnvironmentalId() {
        return environmentalId;
    }

    /**
     * 环境监测机构信息表
     * @param environmentalId 环境监测机构信息表
     */
    public void setEnvironmentalId(Long environmentalId) {
        this.environmentalId = environmentalId;
    }

    /**
     * 机构名称
     * @return environmental_name 机构名称
     */
    public String getEnvironmentalName() {
        return environmentalName;
    }

    /**
     * 机构名称
     * @param environmentalName 机构名称
     */
    public void setEnvironmentalName(String environmentalName) {
        this.environmentalName = environmentalName == null ? null : environmentalName.trim();
    }

    /**
     * 服务对象
     * @return environmental_server 服务对象
     */
    public String getEnvironmentalServer() {
        return environmentalServer;
    }

    /**
     * 服务对象
     * @param environmentalServer 服务对象
     */
    public void setEnvironmentalServer(String environmentalServer) {
        this.environmentalServer = environmentalServer == null ? null : environmentalServer.trim();
    }

    /**
     * 机构地址
     * @return environmental_address 机构地址
     */
    public String getEnvironmentalAddress() {
        return environmentalAddress;
    }

    /**
     * 机构地址
     * @param environmentalAddress 机构地址
     */
    public void setEnvironmentalAddress(String environmentalAddress) {
        this.environmentalAddress = environmentalAddress == null ? null : environmentalAddress.trim();
    }

    /**
     * 联系人
     * @return environmental_linkman 联系人
     */
    public String getEnvironmentalLinkman() {
        return environmentalLinkman;
    }

    /**
     * 联系人
     * @param environmentalLinkman 联系人
     */
    public void setEnvironmentalLinkman(String environmentalLinkman) {
        this.environmentalLinkman = environmentalLinkman == null ? null : environmentalLinkman.trim();
    }

    /**
     * 联系人电话
     * @return environmental_linkman_phone 联系人电话
     */
    public Long getEnvironmentalLinkmanPhone() {
        return environmentalLinkmanPhone;
    }

    /**
     * 联系人电话
     * @param environmentalLinkmanPhone 联系人电话
     */
    public void setEnvironmentalLinkmanPhone(Long environmentalLinkmanPhone) {
        this.environmentalLinkmanPhone = environmentalLinkmanPhone;
    }
}