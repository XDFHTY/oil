package com.cj.organization.entity;

import com.cj.common.utils.excle.IsNeeded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 导入作业区和账号关联 实体类
 * Created by XD on 2018/10/11.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImprotAreaAdminInfo implements Serializable {
    /**
     * 厂、矿名称
     */
    @IsNeeded
    private String factoryName;
    /**
     *作业区名称
     */
    @IsNeeded
    private String taskAreaName;
    /**
     * 账号
     */
    @IsNeeded
    private String adminName;
    /**
     * 姓名
     */
    @IsNeeded
    private String fullName;

    /**
     * 角色
     */
    @IsNeeded
    private String roleName;

    /**
     * 手机号
     */
    @IsNeeded
    private String phone;
}
