package com.cj.organization.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 组织机构和账号  详细实体类 用于接收查询结果
 * Created by XD on 2018/11/12.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminAndOrganInfo {
    private Long adminId;

    private String adminName;

    private Integer roleId;

    private String roleName;

    private String popedomGrade;

    private String fullName;

    private String phone;

    //分公司id 1
    private Long b1Id;
    //分公司名称 1
    private String b1Name;
    //气矿id 1
    private Long f1Id;
    //气矿名称 1
    private String f1Name;
    //作业区id 1
    private Long t1Id;
    //作业区名称 1
    private String t1Name;
    //场站id
    private Long s1Id;
    //场站名称
    private String s1Name;
    //工艺分类id
    private Long factoryTypeId;
    //工艺分类名称
    private String factoryTypeName;

    //分公司id 2
    private Long b2Id;
    //分公司名称 2
    private String b2Name;
    //气矿id 2
    private Long f2Id;
    //气矿名称 2
    private String f2Name;
    //作业区id 2
    private Long t2Id;
    //作业区名称 2
    private String t2Name;


    //分公司id 3
    private Long b3Id;
    //分公司名称 3
    private String b3Name;
    //气矿id 3
    private Long f3Id;
    //气矿名称 3
    private String f3Name;


    //分公司id 4
    private Long b4Id;
    //分公司名称 4
    private String b4Name;
}
