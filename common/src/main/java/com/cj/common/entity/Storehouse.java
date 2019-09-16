package com.cj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

@ApiModel(value = "储备库表")
public class Storehouse  implements Serializable {
    /**
     * 储备库表
     */
    @ApiModelProperty(name = "storehouseId",value = "ID",dataType = "Long")
    private Long storehouseId;

    /**
     * 所属矿，厂ID
     */
    @ApiModelProperty(name = "factoryId",value = "所属矿，厂ID",dataType = "Long")
    private Long factoryId;

    /**
     * 作业区ID
     */
    @ApiModelProperty(name = "taskAreaId",value = "作业区ID",dataType = "Long")
    private Long taskAreaId;

    /**
     * 场站ID
     */
    @ApiModelProperty(name = "stationId",value = "场站ID",dataType = "Long")
    private Long stationId;

    /**
     * 储备库名称
     */
    @ApiModelProperty(name = "storehouseName",value = "储备库名称",dataType = "String")
    private String storehouseName;

    /**
     * 储物库级别
     */
    @ApiModelProperty(name = "storehouseLeave",value = "储物库级别",dataType = "String")
    private String storehouseLeave;

    /**
     * 储物库地址
     */
    @ApiModelProperty(name = "storehouseAddress",value = "储物库地址",dataType = "String")
    private String storehouseAddress;

    /**
     * 储物库联系人
     */
    @ApiModelProperty(name = "storehouseLinkman",value = "储物库联系人",dataType = "String")
    private String storehouseLinkman;

    /**
     * 储物库联系人电话
     */
    @ApiModelProperty(name = "storehouseLinkmanTelphone",value = "储物库联系人电话",dataType = "Long")
    @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$",message = "电话号码格式错误")
    @NotBlank(message = "电话号码不能为空")
    private String storehouseLinkmanTelphone;

    /**
     * 储备库表
     * @return storehouse_id 储备库表
     */
    public Long getStorehouseId() {
        return storehouseId;
    }

    /**
     * 储备库表
     * @param storehouseId 储备库表
     */
    public void setStorehouseId(Long storehouseId) {
        this.storehouseId = storehouseId;
    }

    /**
     * 所属矿，厂ID
     * @return factory_id 所属矿，厂ID
     */
    public Long getFactoryId() {
        return factoryId;
    }

    /**
     * 所属矿，厂ID
     * @param factoryId 所属矿，厂ID
     */
    public void setFactoryId(Long factoryId) {
        this.factoryId = factoryId;
    }

    /**
     * 作业区ID
     * @return task_area_id 作业区ID
     */
    public Long getTaskAreaId() {
        return taskAreaId;
    }

    /**
     * 作业区ID
     * @param taskAreaId 作业区ID
     */
    public void setTaskAreaId(Long taskAreaId) {
        this.taskAreaId = taskAreaId;
    }

    /**
     * 场站ID
     * @return station_id 场站ID
     */
    public Long getStationId() {
        return stationId;
    }

    /**
     * 场站ID
     * @param stationId 场站ID
     */
    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    /**
     * 储备库名称
     * @return storehouse_name 储备库名称
     */
    public String getStorehouseName() {
        return storehouseName;
    }

    /**
     * 储备库名称
     * @param storehouseName 储备库名称
     */
    public void setStorehouseName(String storehouseName) {
        this.storehouseName = storehouseName == null ? null : storehouseName.trim();
    }

    /**
     * 储物库级别
     * @return storehouse_leave 储物库级别
     */
    public String getStorehouseLeave() {
        return storehouseLeave;
    }

    /**
     * 储物库级别
     * @param storehouseLeave 储物库级别
     */
    public void setStorehouseLeave(String storehouseLeave) {
        this.storehouseLeave = storehouseLeave == null ? null : storehouseLeave.trim();
    }

    /**
     * 储物库地址
     * @return storehouse_address 储物库地址
     */
    public String getStorehouseAddress() {
        return storehouseAddress;
    }

    /**
     * 储物库地址
     * @param storehouseAddress 储物库地址
     */
    public void setStorehouseAddress(String storehouseAddress) {
        this.storehouseAddress = storehouseAddress == null ? null : storehouseAddress.trim();
    }

    /**
     * 储物库联系人
     * @return storehouse_linkman 储物库联系人
     */
    public String getStorehouseLinkman() {
        return storehouseLinkman;
    }

    /**
     * 储物库联系人
     * @param storehouseLinkman 储物库联系人
     */
    public void setStorehouseLinkman(String storehouseLinkman) {
        this.storehouseLinkman = storehouseLinkman == null ? null : storehouseLinkman.trim();
    }

    /**
     * 储物库联系人电话
     * @return storehouse_linkman_telphone 储物库联系人电话
     */
    public String getStorehouseLinkmanTelphone() {
        return storehouseLinkmanTelphone;
    }

    /**
     * 储物库联系人电话
     * @param storehouseLinkmanTelphone 储物库联系人电话
     */
    public void setStorehouseLinkmanTelphone(String storehouseLinkmanTelphone) {
        this.storehouseLinkmanTelphone = storehouseLinkmanTelphone;
    }
}