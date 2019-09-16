package com.cj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "admin-user表")
public class Key64  implements Serializable {
    /**
     * admin-user-唯一主键
     */
    @ApiModelProperty(name = "id",value = "id",dataType = "Long")
    private Long id;

    /**
     * 
     */
    @ApiModelProperty(name = "stub",value = "",dataType = "String")
    private String stub;

    /**
     * admin-user-唯一主键
     * @return id admin-user-唯一主键
     */
    public Long getId() {
        return id;
    }

    /**
     * admin-user-唯一主键
     * @param id admin-user-唯一主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return stub 
     */
    public String getStub() {
        return stub;
    }

    /**
     * 
     * @param stub 
     */
    public void setStub(String stub) {
        this.stub = stub == null ? null : stub.trim();
    }
}