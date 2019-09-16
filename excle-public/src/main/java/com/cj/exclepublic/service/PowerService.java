package com.cj.exclepublic.service;

import com.cj.core.domain.ApiResult;
import com.cj.exclepublic.domain.FindOrganizationResp;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by XD on 2018/10/15.
 * 业务权限检查
 */
public interface PowerService {
    //带权限查询各个机构名称，控制页面下拉框参数选项
    ApiResult getOrganizationList(HttpServletRequest request);

    /**
     * 返回true为可以编辑
     * 检查当前表状态 此角色等级是否可编辑，控制前4个模块表格编辑操作权限
     * @param checkType 当前表状态
     * @param roleGradeName 用户角色等级
     * @return
     */
    public Boolean checkUpdatePower(String checkType,String roleGradeName);


    //根据场站ID查询组织结构信息
    public FindOrganizationResp findOrganization(Long stationId);
}
