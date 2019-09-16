package com.cj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "厂、矿分类信息表")
public class FactoryType  implements Serializable {
    /**
     * 厂、矿分类信息表
     */
    @ApiModelProperty(name = "factoryId",value = "id",dataType = "Long")
    private Long factoryTypeId;

    /**
     * 分类名称
     */
    @ApiModelProperty(name = "factoryTypeName",value = "分类名称",dataType = "String")
    private String factoryTypeName;

    /**
     * 类型
     */
    @ApiModelProperty(name = "factoryType",value = "类型",dataType = "String")
    private String factoryType;

    /**
     * 厂、矿分类信息表
     * @return factory_type_id 厂、矿分类信息表
     */
    public Long getFactoryTypeId() {
        return factoryTypeId;
    }

    /**
     * 厂、矿分类信息表
     * @param factoryTypeId 厂、矿分类信息表
     */
    public void setFactoryTypeId(Long factoryTypeId) {
        this.factoryTypeId = factoryTypeId;
    }

    /**
     * 分类名称
     * @return factory_type_name 分类名称
     */
    public String getFactoryTypeName() {
        return factoryTypeName;
    }

    /**
     * 分类名称
     * @param factoryTypeName 分类名称
     */
    public void setFactoryTypeName(String factoryTypeName) {
        this.factoryTypeName = factoryTypeName == null ? null : factoryTypeName.trim();
    }

    /**
     * 类型
     * @return factory_type 类型
     */
    public String getFactoryType() {
        return factoryType;
    }

    /**
     * 类型
     * @param factoryType 类型
     */
    public void setFactoryType(String factoryType) {
        this.factoryType = factoryType == null ? null : factoryType.trim();
    }
}