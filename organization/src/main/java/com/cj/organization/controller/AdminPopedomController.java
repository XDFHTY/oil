package com.cj.organization.controller;

import com.cj.core.domain.ApiResult;
import com.cj.core.domain.Pager;
import com.cj.core.aop.Log;
import com.cj.organization.entity.AdminAndOrgan;
import com.cj.organization.service.AdminPopedomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 组织机构和账号的关系操作 接口
 * Created by XD on 2018/10/12.
 */
@RestController
@RequestMapping("/api/v1/admin/account")
@Api(tags = "账号、角色、权限模块: 账号管理")
public class AdminPopedomController {
    @Autowired
    private AdminPopedomService adminPopedomService;



    @ApiOperation(value = "分页条件查询 账号列表",response = AdminAndOrgan.class)
    @PostMapping("/findAdminInfoByName")
    @Log(name = "账号管理 ==> 条件查询 账号列表")
    public ApiResult findAdminInfoByName(
            @ApiParam(name = "pager", value = "查询条件parameters=" +"factoryId=气矿id（非必传）," +
                    "areaId=作业区id（非必传）" +
                    "stationId=场站id（非必传）(三个id中至少传一个)，" +
                    "name=姓名(非必传)"
                    ,required = true)
            @RequestBody Pager pager, HttpServletRequest request){
        ApiResult apiResult = adminPopedomService.findAdminByName(pager,request);
        return apiResult;
    }


    @ApiOperation("修改 机构对应账号的关系")
    @PutMapping("/updateAdminInfo")
    @Log(name = "账号管理 ==> 修改 机构对应的账号")
    public ApiResult updateAdminInfo(
            @ApiParam(name = "map", value ="branchCompanyId=分公司id " +
                    "factoryId=气矿id（非必传）," +
                    "areaId=作业区id（非必传）" +
                    "stationId=场站id（非必传）(三个id中至少传一个)，" +
                    "adminId=管理员id  roleId=角色id(必传)"
                    ,required = true)
            @RequestBody Map map, HttpServletRequest request){

        ApiResult apiResult = adminPopedomService.updateAdmin(map,request);
        return apiResult;
    }

    @ApiOperation("修改 账号附属信息")
    @PutMapping("/updateAdminSubsidiaryInfo")
    @Log(name = "账号管理 ==> 修改 账号附属信息")
    public ApiResult updateAdminSubsidiaryInfo(
            @ApiParam(name = "map", value =
                    "fullName=姓名（非必传）," +
                    "phone=手机号（非必传） adminId=需要修改的账号id" +
                    "adminPass=密码   adminName=用户名"
                    ,required = true)
            @RequestBody Map map, HttpServletRequest request){
        ApiResult apiResult = adminPopedomService.updateAdminSubsidiaryInfo(map,request);
        return apiResult;
    }


    @ApiOperation("删除 机构对应的账号关系")
    @DeleteMapping("/deleteAdminInfo")
    @Log(name = "账号管理 ==> 删除 机构对应的账号")
    public ApiResult deleteAdminInfo(
            @ApiParam(name = "map", value = "branchCompanyId=分公司id " +
                    "factoryId=气矿id（非必传）," +
                    "areaId=作业区id（非必传）" +
                    "stationId=场站id（非必传），" +
                    "adminId=管理员id  "
                    ,required = true)
            @RequestBody Map map, HttpServletRequest request){

        ApiResult apiResult = adminPopedomService.deleteAdmin(map,request);
        return apiResult;
    }


    @ApiOperation("新增 机构对应的账号和关系")
    @PostMapping("/addAdminInfo")
    @Log(name = "账号管理 ==> 新增 机构对应的账号和关系")
    public ApiResult addAdminInfo(
            @ApiParam(name = "map", value = "branchCompanyId=分公司id" +
                    "factoryId=气矿id（非必传）," +
                    "areaId=作业区id（非必传）" +
                    "stationId=场站id（非必传）(三个id中至少传一个)，" +
                    "adminName=用户名 adminPass=密码  fullName=姓名   phone=手机号  roleId=角色id"
                    ,required = true)
            @RequestBody Map map, HttpServletRequest request){

        ApiResult apiResult = adminPopedomService.addAdminInfo(map,request);
        return apiResult;
    }





}
