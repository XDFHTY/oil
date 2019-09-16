package com.cj.organization.service;

import com.cj.core.domain.ApiResult;
import com.cj.core.domain.Pager;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 组织结构信息  场站管理
 * Created by XD on 2018/10/9.
 */
public interface StationService {
    //条件查询 气矿基本信息、作业区、场站和管理人信息
    ApiResult findStationById(Pager p, HttpServletRequest request);

    //移除账号关联
    ApiResult removeAdmin(Map map, HttpServletRequest request);

    //关联账号
    ApiResult relationAdmin(Map map, HttpServletRequest request);

    //按姓名模糊筛选账号列表
    ApiResult selectAdmin(Map map, HttpServletRequest request);

    //要删除的组织  必须管理人 及下属机构的管理人 都要被移除后才能删除
    ApiResult deleteOrganization(Map map, HttpServletRequest request);

    //新增组织机构
    ApiResult addOrganization(Map map, HttpServletRequest request);

    //查询所有工艺分类
    ApiResult getFactoryType(HttpServletRequest request);

    //修改组织机构名称
    ApiResult updateOrganization(Map map, HttpServletRequest request);

    //分页条件查询 气矿基本信息、作业区、场站和管理人信息
    ApiResult findOrganizationById(Pager p, HttpServletRequest request);
}
