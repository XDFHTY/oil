package com.cj.common.entity;

import com.cj.common.domain.RegisterInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "用户实体类")
public class User  implements Serializable {
    public User(){};
    public User(RegisterInfo info){
        this.userName=info.getUserName();
        this.userPass=info.getUserPass();
        this.phoneNumber = info.getPhoneNumber();
    }
    /**
     * 用户表
     */
    @ApiModelProperty(name = "userId",value = "ID",dataType = "Long")
    private Long userId;

    /**
     * 用户名
     */
    @ApiModelProperty(name = "userName",value = "用户名",dataType = "String")
    private String userName;

    /**
     * 密码
     */
    @ApiModelProperty(name = "userPass",value = "密码",dataType = "String")
    private String userPass;

    /**
     * 盐值
     */
    @ApiModelProperty(name = "saltVal",value = "盐值",dataType = "String")
    private String saltVal;

    /**
     * 用户类型-普通用户
     */
    @ApiModelProperty(name = "userType",value = "用户类型-普通用户",dataType = "String")
    private String userType;

    /**
     * 账号有效期
     */
    @ApiModelProperty(name = "validTime",value = "账号有效期",dataType = "Date")
    private Date validTime;

    /**
     * 手机号
     */
    @ApiModelProperty(name = "phoneNumber",value = "手机号",dataType = "String")
    private String phoneNumber;

    /**
     * 是否验证，0-未验证，1-已验证，默认为0
     */
    @ApiModelProperty(name = "isPhone",value = "是否验证，0-未验证，1-已验证，默认为0",dataType = "String")
    private String isPhone;

    /**
     * 邮箱
     */
    @ApiModelProperty(name = "eMail",value = "邮箱",dataType = "String")
    private String eMail;

    /**
     * 是否验证，0-未验证，1-已验证，默认为0
     */
    @ApiModelProperty(name = "isMail",value = "是否验证，0-未验证，1-已验证，默认为0",dataType = "String")
    private String isMail;

    /**
     * QQ号
     */
    @ApiModelProperty(name = "qqNumber",value = "QQ号",dataType = "String")
    private String qqNumber;

    /**
     * WX号
     */
    @ApiModelProperty(name = "wxNumber",value = "WX号",dataType = "String")
    private String wxNumber;

    /**
     * WB号
     */
    @ApiModelProperty(name = "wbNumber",value = "WB号",dataType = "String")
    private String wbNumber;

    /**
     * 账号状态，0-已删除，1-正常
     */
    @ApiModelProperty(name = "state",value = "账号状态，0-已删除，1-正常",dataType = "String")
    private String state;

    /**
     * 操作员ID
     */
    @ApiModelProperty(name = "operationId",value = "操作员ID",dataType = "Long")
    private Long operationId;

    /**
     * 创建时间
     */
    @ApiModelProperty(name = "createTime",value = "创建时间",dataType = "Date")
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(name = "updateTime",value = "修改时间",dataType = "Date")
    private Date updateTime;

    /**
     * 用户表
     * @return user_id 用户表
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 用户表
     * @param userId 用户表
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 用户名
     * @return user_name 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 用户名
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 密码
     * @return user_pass 密码
     */
    public String getUserPass() {
        return userPass;
    }

    /**
     * 密码
     * @param userPass 密码
     */
    public void setUserPass(String userPass) {
        this.userPass = userPass == null ? null : userPass.trim();
    }

    /**
     * 盐值
     * @return salt_val 盐值
     */
    public String getSaltVal() {
        return saltVal;
    }

    /**
     * 盐值
     * @param saltVal 盐值
     */
    public void setSaltVal(String saltVal) {
        this.saltVal = saltVal == null ? null : saltVal.trim();
    }

    /**
     * 用户类型-普通用户
     * @return user_type 用户类型-普通用户
     */
    public String getUserType() {
        return userType;
    }

    /**
     * 用户类型-普通用户
     * @param userType 用户类型-普通用户
     */
    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    /**
     * 账号有效期
     * @return valid_time 账号有效期
     */
    public Date getValidTime() {
        return validTime;
    }

    /**
     * 账号有效期
     * @param validTime 账号有效期
     */
    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }

    /**
     * 手机号
     * @return phone_number 手机号
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * 手机号
     * @param phoneNumber 手机号
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    /**
     * 是否验证，0-未验证，1-已验证，默认为0
     * @return is_phone 是否验证，0-未验证，1-已验证，默认为0
     */
    public String getIsPhone() {
        return isPhone;
    }

    /**
     * 是否验证，0-未验证，1-已验证，默认为0
     * @param isPhone 是否验证，0-未验证，1-已验证，默认为0
     */
    public void setIsPhone(String isPhone) {
        this.isPhone = isPhone == null ? null : isPhone.trim();
    }

    /**
     * 邮箱
     * @return e_mail 邮箱
     */
    public String geteMail() {
        return eMail;
    }

    /**
     * 邮箱
     * @param eMail 邮箱
     */
    public void seteMail(String eMail) {
        this.eMail = eMail == null ? null : eMail.trim();
    }

    /**
     * 是否验证，0-未验证，1-已验证，默认为0
     * @return is_mail 是否验证，0-未验证，1-已验证，默认为0
     */
    public String getIsMail() {
        return isMail;
    }

    /**
     * 是否验证，0-未验证，1-已验证，默认为0
     * @param isMail 是否验证，0-未验证，1-已验证，默认为0
     */
    public void setIsMail(String isMail) {
        this.isMail = isMail == null ? null : isMail.trim();
    }

    /**
     * QQ号
     * @return qq_number QQ号
     */
    public String getQqNumber() {
        return qqNumber;
    }

    /**
     * QQ号
     * @param qqNumber QQ号
     */
    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber == null ? null : qqNumber.trim();
    }

    /**
     * WX号
     * @return wx_number WX号
     */
    public String getWxNumber() {
        return wxNumber;
    }

    /**
     * WX号
     * @param wxNumber WX号
     */
    public void setWxNumber(String wxNumber) {
        this.wxNumber = wxNumber == null ? null : wxNumber.trim();
    }

    /**
     * WB号
     * @return wb_number WB号
     */
    public String getWbNumber() {
        return wbNumber;
    }

    /**
     * WB号
     * @param wbNumber WB号
     */
    public void setWbNumber(String wbNumber) {
        this.wbNumber = wbNumber == null ? null : wbNumber.trim();
    }

    /**
     * 账号状态，0-已删除，1-正常
     * @return state 账号状态，0-已删除，1-正常
     */
    public String getState() {
        return state;
    }

    /**
     * 账号状态，0-已删除，1-正常
     * @param state 账号状态，0-已删除，1-正常
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * 操作员ID
     * @return operation_id 操作员ID
     */
    public Long getOperationId() {
        return operationId;
    }

    /**
     * 操作员ID
     * @param operationId 操作员ID
     */
    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

    /**
     * 创建时间
     * @return create_time 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 修改时间
     * @return update_time 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 修改时间
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}