package com.cj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "excle列表-工艺ID中间表对象")
public class AuthExcleFactory implements Serializable {
    /**
     * excle列表-工艺ID中间表
     */
    @ApiModelProperty(name = "authExcleFactoryId",value = "ID",dataType = "Long")
    private Long authExcleFactoryId;

    /**
     * excle列表ID
     */
    @ApiModelProperty(name = "excleTableId",value = "excle列表ID",dataType = "Long")
    private Long excleTableId;

    /**
     * 工艺ID
     */
    @ApiModelProperty(name = "factoryTypeId",value = "工艺ID",dataType = "Long")
    private Long factoryTypeId;

    /**
     * excle列表-工艺ID中间表
     * @return auth_excle_factory_id excle列表-工艺ID中间表
     */
    public Long getAuthExcleFactoryId() {
        return authExcleFactoryId;
    }

    /**
     * excle列表-工艺ID中间表
     * @param authExcleFactoryId excle列表-工艺ID中间表
     */
    public void setAuthExcleFactoryId(Long authExcleFactoryId) {
        this.authExcleFactoryId = authExcleFactoryId;
    }

    /**
     * excle列表ID
     * @return excle_table_id excle列表ID
     */
    public Long getExcleTableId() {
        return excleTableId;
    }

    /**
     * excle列表ID
     * @param excleTableId excle列表ID
     */
    public void setExcleTableId(Long excleTableId) {
        this.excleTableId = excleTableId;
    }

    /**
     * 工艺ID
     * @return factory_type_id 工艺ID
     */
    public Long getFactoryTypeId() {
        return factoryTypeId;
    }

    /**
     * 工艺ID
     * @param factoryTypeId 工艺ID
     */
    public void setFactoryTypeId(Long factoryTypeId) {
        this.factoryTypeId = factoryTypeId;
    }
}