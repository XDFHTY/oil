package com.cj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "外环境关系图对象")
public class ExternalEnvirImg  implements Serializable {
    /**
     * 外环境关系图表
     */
    @ApiModelProperty(name = "externalEnvirImgId",value = "id",dataType = "Long")
    private Long externalEnvirImgId;

    /**
     * 表内容列表ID
     */
    @ApiModelProperty(name = "tableRecordId",value = "表内容列表ID",dataType = "Long")
    private Long tableRecordId;

    /**
     * 图片url
     */
    @ApiModelProperty(name = "imgUrl",value = "图片url",dataType = "String")
    private String imgUrl;

    /**
     * 状态，0-已删除，1-正常，默认为1
     */
    @ApiModelProperty(name = "state",value = "状态，0-已删除，1-正常，默认为1",dataType = "String")
    private String state;

    /**
     * 外环境关系图表
     * @return external_envir_img_id 外环境关系图表
     */
    public Long getExternalEnvirImgId() {
        return externalEnvirImgId;
    }

    /**
     * 外环境关系图表
     * @param externalEnvirImgId 外环境关系图表
     */
    public void setExternalEnvirImgId(Long externalEnvirImgId) {
        this.externalEnvirImgId = externalEnvirImgId;
    }

    /**
     * 表内容列表ID
     * @return table_record_id 表内容列表ID
     */
    public Long getTableRecordId() {
        return tableRecordId;
    }

    /**
     * 表内容列表ID
     * @param tableRecordId 表内容列表ID
     */
    public void setTableRecordId(Long tableRecordId) {
        this.tableRecordId = tableRecordId;
    }

    /**
     * 图片url
     * @return img_url 图片url
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * 图片url
     * @param imgUrl 图片url
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    /**
     * 状态，0-已删除，1-正常，默认为1
     * @return state 状态，0-已删除，1-正常，默认为1
     */
    public String getState() {
        return state;
    }

    /**
     * 状态，0-已删除，1-正常，默认为1
     * @param state 状态，0-已删除，1-正常，默认为1
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }
}