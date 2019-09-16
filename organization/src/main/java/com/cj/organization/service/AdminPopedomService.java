package com.cj.organization.service;

import com.cj.core.domain.ApiResult;
import com.cj.core.domain.Pager;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by XD on 2018/10/12.
 */
public interface AdminPopedomService {
    //条件查询 账号列表
    ApiResult findAdminByName(Pager pager, HttpServletRequest request);

    //修改 机构对应的账号
    ApiResult updateAdmin(Map map, HttpServletRequest request);

    //删除 机构对应的账号
    ApiResult deleteAdmin(Map map, HttpServletRequest request);

    //新增 机构对应的账号和关系
    ApiResult addAdminInfo(Map map, HttpServletRequest request);

    //修改 账号附属信息
    ApiResult updateAdminSubsidiaryInfo(Map map, HttpServletRequest request);
}
