package com.cj.organization.entity;

import com.cj.common.entity.Admin;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 账号和角色等级 实体类
 * Created by XD on 2018/10/15.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "AdminAndRole 账号和角色等级")
public class AdminAndRole extends Admin implements Serializable {
    //角色等级
    @ApiModelProperty(name = "roleDescriptionName",value = "角色等级",dataType = "String")
    private String roleDescriptionName;
}
