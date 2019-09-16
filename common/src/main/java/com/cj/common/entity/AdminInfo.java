package com.cj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述:admin_info表的实体类
 * @version
 * @author:  XD
 * @创建时间: 2018-10-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "管理员扩展信息对象")
public class AdminInfo implements Serializable {
    /**
     * 管理员扩展信息表
     */
    @ApiModelProperty(name = "adminInfoId",value = "Id",dataType = "Long")
    private Long adminInfoId;

    /**
     * 管理员id
     */
    @ApiModelProperty(name = "adminId",value = "管理员id",dataType = "Long")
    private Long adminId;

    /**
     * 管理员姓名
     */
    @ApiModelProperty(name = "fullName",value = "管理员姓名",dataType = "String")
    private String fullName;

    /**
     * 管理员电话
     */
    @ApiModelProperty(name = "phone",value = "管理员电话",dataType = "String")
    private String phone;

    /**
     * 管理员扩展信息表
     * @return admin_info_id 管理员扩展信息表
     */
    public Long getAdminInfoId() {
        return adminInfoId;
    }

    /**
     * 管理员扩展信息表
     * @param adminInfoId 管理员扩展信息表
     */
    public void setAdminInfoId(Long adminInfoId) {
        this.adminInfoId = adminInfoId;
    }

    /**
     * 管理员id
     * @return admin_id 管理员id
     */
    public Long getAdminId() {
        return adminId;
    }

    /**
     * 管理员id
     * @param adminId 管理员id
     */
    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    /**
     * 管理员姓名
     * @return full_name 管理员姓名
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 管理员姓名
     * @param fullName 管理员姓名
     */
    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
    }

    /**
     * 管理员电话
     * @return phone 管理员电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 管理员电话
     * @param phone 管理员电话
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }
}