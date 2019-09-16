package com.cj.common.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**用户注册相关信息
 * Created by zy
 */
@Data
@ApiModel(value = "用户实体类")
public class RegisterInfo  implements Serializable {
    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     *
     */
    private String userPass;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * 手机号

     */
    private String phoneNumber;

    /**
     * 验证码
     */
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }
}
