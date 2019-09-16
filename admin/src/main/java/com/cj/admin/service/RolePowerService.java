package com.cj.admin.service;

import com.cj.admin.domain.UpdateModularByRoleIdReq;
import com.cj.common.entity.AuthRole;
import com.cj.core.domain.ApiResult;

public interface RolePowerService {

    //====================================角色管理

    /**
     * 查询所有角色信息
     */
    public ApiResult findAllRole();

    /**
     * 新增角色
     */
    public ApiResult addRole(AuthRole authRole);

    /**
     * 删除角色
     */
    public ApiResult deleteRole(Long roleId);


    //======================================权限管理

    /**
     * 根据角色ID查询权限列表
     */
    public ApiResult findModularByRoleId(String json);

    /**
     * 根据角色ID修改角色权限
     * @param updateModularByRoleIdReq
     * @return
     */
    public ApiResult updateModularByRoleId(UpdateModularByRoleIdReq updateModularByRoleIdReq);


    /**
     * 从新读取权限信息
     * @return
     */
    public ApiResult readRolePower();
}
