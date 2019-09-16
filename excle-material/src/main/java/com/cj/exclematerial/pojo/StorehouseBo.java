package com.cj.exclematerial.pojo;

import com.cj.exclematerial.annotation.ExcelField;
import com.cj.exclematerial.annotation.ExcelSheet;
import lombok.Data;

@Data
@ExcelSheet("气矿储物库分布情况表")
public class StorehouseBo {
    /**
     * 储备库表
     */

    private Long storehouseId;

    @ExcelField("序号")
    private int num;

    /**
     * 所属矿，厂ID
     */

    private Long factoryId;

    @ExcelField("所属气矿")
    private String factoryName;

    /**
     * 储备库名称
     */
    @ExcelField("储备库名称")
    private String storehouseName;

    /**
     * 储物库级别
     */
    @ExcelField("储物库级别")
    private String storehouseLeave;

    /**
     * 储物库地址
     */
    @ExcelField("储物库地址")
    private String storehouseAddress;

    /**
     * 储物库联系人
     */
    @ExcelField("储物库联系人")
    private String storehouseLinkman;

    /**
     * 储物库联系人电话
     */
    @ExcelField("储物库联系人电话")
    private Long storehouseLinkmanTelphone;

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
    public Long getStorehouseLinkmanTelphone() {
        return storehouseLinkmanTelphone;
    }

    /**
     * 储物库联系人电话
     * @param storehouseLinkmanTelphone 储物库联系人电话
     */
    public void setStorehouseLinkmanTelphone(Long storehouseLinkmanTelphone) {
        this.storehouseLinkmanTelphone = storehouseLinkmanTelphone;
    }
}