package com.cj.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 系统所有权限
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户实体类")
public class AuthModulars implements Serializable {

    /**
     * 权限资源表
     */
    @ApiModelProperty(name = "modularId",value = "Id",dataType = "Integer")
    private Integer modularId;

    /**
     * 名称
     */
    @ApiModelProperty(name = "pageName",value = "名称",dataType = "String")
    private String pageName;

    /**
     * 请求路径
     */
    @ApiModelProperty(name = "pageUrl",value = "请求路径",dataType = "String")
    private String pageUrl;

    /**
     * 请求方式resful方法头
     */
    @ApiModelProperty(name = "pageMethod",value = "请求方式resful方法头",dataType = "String")
    private String pageMethod;

    /**
     * 分类，1-一级目录，0-请求
     */
    @ApiModelProperty(name = "pageType",value = "分类，1-一级目录，0-请求",dataType = "String")
    private String pageType;

    /**
     * 排序
     */
    @ApiModelProperty(name = "pageSort",value = "排序",dataType = "Integer")
    private Integer pageSort;

    /**
     * 父级页面ID
     */
    @ApiModelProperty(name = "parentId",value = "父级页面ID",dataType = "Integer")
    private Integer parentId;

    /**
     * 备用
     */
    @ApiModelProperty(name = "spare1",value = "备用",dataType = "String")
    private String spare1;

    /**
     * 状态（0-已删除，1-正常）
     */
    @ApiModelProperty(name = "state",value = "状态（0-已删除，1-正常）",dataType = "String")
    private String state;

    /**
     * 创建时间
     */
    @ApiModelProperty(name = "createTime",value = "创建时间",dataType = "Date")
    private Date createTime;

    /**
     * 子节点
     */

    @ApiModelProperty(name = "children",value = "子节点",dataType = "com.cj.common.domain.AuthModulars" )
    private List<AuthModulars> children;


}
