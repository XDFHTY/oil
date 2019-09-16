package com.cj.admin.controller;

import com.cj.admin.domain.FindAllRoleResp;
import com.cj.admin.domain.UpdateModularByRoleIdReq;
import com.cj.admin.service.RolePowerService;
import com.cj.common.domain.AuthRoleModulars;
import com.cj.common.entity.AuthRole;
import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/admin/rolepower")
@Api(tags = "账号、角色、权限模块: 角色、权限管理")
public class RolePowerController {

    @Resource
    private RolePowerService rolePowerService;


    //=========================================角色管理

    /**
     * 查询所有角色信息
     * 参数：无
     * 返回：角色对象集合
     */
    @ApiOperation(value = "查询所有角色信息以及系统权限列表",response = FindAllRoleResp.class)
    @GetMapping("/findAllRole")
    @Log(name = "角色、权限管理",value = "查询所有角色信息以及系统权限列表")
    public ApiResult findAllRole(){

        return rolePowerService.findAllRole();

    }

    /**
     * 新增角色
     * 参数：角色名、角色等级
     * 校验：角色名是否重复
     * 返回：成功/失败
     */
    @ApiOperation("新增角色")
    @PostMapping("/addRole")
    @Log(name = "角色、权限管理",value = "新增角色")
    public ApiResult addRole(@ApiParam(name = "authRole",value = "roleName=角色名、roleDescriptionName=角色等级" +
            "\n{\n" +
            "  \"roleDescriptionName\": \"作业区级\",\n" +
            "  \"roleName\": \"测试角色\"\n" +
            "}" +
            "")
                            @RequestBody AuthRole authRole){

        return rolePowerService.addRole(authRole);

    }

    /**
     * 删除角色
     * 参数：角色ID
     * 校验：是否有人在使用此角色
     * 返回：成功/失败
     */
    @ApiOperation("删除角色")
    @DeleteMapping("/deleteRole")
    @Log(name = "角色、权限管理",value = "删除角色")
    public ApiResult deleteRole(Long roleId){

        return rolePowerService.deleteRole(roleId);

    }

//    /**
//     * 修改角色
//     * 参数：角色ID、角色等级、新角色名
//     * 校验：角色名是否重复
//     * 返回：成功/失败
//     */
//    @ApiOperation("修改角色信息")
//    @PutMapping("/updateRole")
//    public ApiResult updateRole(@ApiParam(name = "authRole",value = "",required = true)
//                               @RequestBody AuthRole authRole){
//
//        return rolePowerService.updateModularByRoleId()
//
//    }


    //======================================权限管理

    /**
     * 根据角色ID查询权限列表
     * 参数：角色ID
     * 返回：角色下权限集合
     */
    @GetMapping("/findModularByRoleId")
    @ApiOperation(value = "根据角色ID查询权限列表",response = AuthRoleModulars.class)
    @Log(name = "角色、权限管理",value = "根据角色ID查询权限列表")
    @ApiImplicitParam(name = "json",value = "角色ID",required = true,defaultValue = "{'roleId':1}")
    public ApiResult findModularByRoleId(String json){

        return rolePowerService.findModularByRoleId(json);

    }

    /**
     * 根据角色ID修改角色权限
     * 参数：角色ID、新权限ID集合
     * service：原权限信息物理删除，新权限将其覆盖。修改成功刷新内存中的权限信息
     * 返回：成功/失败
     */
    @PutMapping("/updateModular")
    @ApiOperation(value = "根据角色ID修改角色权限")
    @Log(name = "角色、权限管理",value = "根据角色ID修改角色权限")
    public ApiResult updateModularByRoleId(@ApiParam(name = "json",value = "{\"roleId\":1," +
            "\"modularIds\":[204,205,206,207,208,209,210,211,212,213,214,215,216,217,218,219,220,221,222,223,224,225,226,227,228,229,230,231,232,233,234,235,236,237,238,239,240,241,242,243,244,245,246,247,248,249,250,251,252,253,254,255,256,257,258,259,260,261,262,263,264,265,266,267,268,269,270,271,272,273,274,275,276,277,278,279,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294]}"
            ,required = true)
                                            @RequestBody UpdateModularByRoleIdReq updateModularByRoleIdReq){

        return rolePowerService.updateModularByRoleId(updateModularByRoleIdReq);
    }

    @GetMapping("/readRolePower")
    @ApiOperation("重新读取权限信息")
    @Log(name = "角色、权限管理",value = "根据角色ID查询权限列表")
    public ApiResult readRolePower(){

        return rolePowerService.readRolePower();

    }

}
