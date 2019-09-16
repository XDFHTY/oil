package com.cj.organization.controller;

import com.cj.core.domain.ApiResult;
import com.cj.core.domain.Pager;
import com.cj.common.utils.file.FileUtil;
import com.cj.core.aop.Log;
import com.cj.organization.entity.RespAdmin;
import com.cj.organization.entity.RespStation;
import com.cj.organization.service.StationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("api/v1/organization/info")
@Api(tags = "组织结构信息管理 ==> 组织结构信息管理")
public class StationController {

    @Autowired
    private StationService stationService;

    @Value("${web.upload-path}")
    private String path; //文件下载路径，配置文件


    /**
     * 根据气矿Id、作业区id、场站 查询 气矿、作业区、场站和管理人信息
     * factoryId=气矿id（非必传，为null则查询全部）
     * areaId=作业区id（非必传，为null则查气矿下所有作业区）
     * stationId=场站/中心站Id（非必传，为null则查全部）
     * popedomId=辖区等级（有stationId则必传）
     * @param p
     * @param request
     * @return
     */
    @ApiOperation(value = "分页条件查询 气矿基本信息、作业区、场站和管理人信息", response = RespStation.class)
    //@PostMapping("/findStationById")
    @Log(name = "场站管理 ==> 条件查询 气矿基本信息、作业区、场站和管理人信息")
    public ApiResult findStationById(
            @ApiParam(name = "p", value = "查询条件=parameters=" +"【factoryId=气矿id（非必传，为null则查询全部）," +
                    "areaId=作业区id（非必传，为null则查全部）," +
                    "stationId=场站Id（非必传，为null则查全部）"
                    ,required = true)
            @RequestBody Pager p, HttpServletRequest request){

        ApiResult apiResult = stationService.findStationById(p,request);
        return apiResult;
    }


    /**
     * 根据气矿Id、作业区id、场站 查询 气矿、作业区、场站和管理人信息
     * factoryId=气矿id（非必传，为null则查询全部）
     * areaId=作业区id（非必传，为null则查气矿下所有作业区）
     * stationId=场站/中心站Id（非必传，为null则查全部）
     * popedomId=辖区等级（有stationId则必传）
     * @param p
     * @param request
     * @return
     */
    @ApiOperation(value = "分页条件查询 气矿基本信息、作业区、场站和管理人信息", response = RespStation.class)
    @PostMapping("/findOrganizationById")
    @Log(name = "场站管理 ==> 条件查询 气矿基本信息、作业区、场站和管理人信息")
    public ApiResult findOrganizationById(
            @ApiParam(name = "p", value = "查询条件=parameters=" +"【factoryId=气矿id（非必传，为null则查询全部）," +
                    "areaId=作业区id（非必传，为null则查全部）," +
                    "stationId=场站Id（非必传，为null则查全部）"
                    ,required = true)
            @RequestBody Pager p, HttpServletRequest request){

        ApiResult apiResult = stationService.findOrganizationById(p,request);
        return apiResult;
    }




    /**
     * 移除关联  前端查询的账号列表  只有同级 和 所属下级 机构的账号
     * 所以只需要判断  需要移除的账号 角色等级是否比 操作人等级小 就可以移除
     * @param map
     * @param request
     * @return
     */
    @ApiOperation("移除账号关联 ")
    @DeleteMapping("/removeAdmin")
    @Log(name = "组织结构信息管理 ==> 移除账号关联")
    public ApiResult removeAdmin(
            @ApiParam(name = "map", value = "adminId=管理员id（必传）," +
                    "stationId=场站id（非必传，如果是移除场站级账号则必传）"
                    ,required = true)
            @RequestBody Map map, HttpServletRequest request){

        ApiResult apiResult = stationService.removeAdmin(map,request);
        return apiResult;
    }


    /**
     * 分公司级 可操作气矿级
     * 气矿级  可操作作业区级
     * 作业区级 可操作场站级
     * 需要操作的账号等级  需要比操作人等级小  才可关联
     * 需要操作的账号等级  需要和准备关联的机构等级 一致
     * @param map
     * @param request
     * @return
     */
    @ApiOperation("关联账号")
    @PutMapping("/relationAdmin")
    @Log(name = "组织结构信息管理 ==> 移除账号关联")
    public ApiResult relationAdmin(
            @ApiParam(name = "map", value = "branchCompanyId=分公司id" +
                    "factoryId=气矿id（非必传）," +
                    "areaId=作业区id（非必传）," +
                    "stationId=场站Id（非必传）(三个至少传一个)" +
                    "adminId=管理员id（必传）,"
                    ,required = true)
            @RequestBody Map map, HttpServletRequest request){

        ApiResult apiResult = stationService.relationAdmin(map,request);
        return apiResult;
    }

    /**
     * 按姓名模糊查询账号列表
     * 需要带上 机构的id  明确  查询哪一级的账号
     * 只能查询比操作人等级小的账号
     * 查询的账号是未绑定过关系的账号
     * @param map
     * @param request
     * @return
     */
    @ApiOperation(value = "按姓名模糊筛选账号列表", response = RespAdmin.class)
    @PostMapping("/selectAdmin")
    @Log(name = "组织结构信息管理 ==> 按姓名模糊查询账号列表")
    public ApiResult selectAdmin(
            @ApiParam(name = "map", value = "branchCompanyId=分公司id" +
                    "factoryId=气矿id（非必传）," +
                    "areaId=作业区id（非必传）," +
                    "stationId=场站Id（非必传）(三个至少传一个)" +
                    "name=管理员姓名（非必传）,"
                    ,required = true)
            @RequestBody Map map, HttpServletRequest request){

        ApiResult apiResult = stationService.selectAdmin(map,request);
        return apiResult;
    }


    @ApiOperation(value = "模版下载", notes = "返回指定路径内的Excel模版文件")
    @Log(name = "组织结构信息管理 ==> 组织机构信息导入模版下载")
    @GetMapping("/downLoadExcel")
    public void downLoadExcel(HttpServletResponse response, HttpServletRequest request){
        String fileName = "organization.xls";
        try {
            new FileUtil().download(request, response, this.path+fileName,fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 要删除的组织  必须管理人 及下属机构的管理人 都要被移除后才能删除
     * @param map
     * @param request
     * @return
     */
    @ApiOperation("删除组织机构 ")
    @DeleteMapping("/deleteOrganization")
    @Log(name = "组织结构信息管理 ==> 删除组织机构")
    public ApiResult deleteOrganization(
            @ApiParam(name = "map", value = "factoryId=气矿id（非必传）," +
                    "areaId=作业区id（非必传）," +
                    "stationId=场站Id（非必传）(三个至少传一个)"
                    ,required = true)
            @RequestBody Map map, HttpServletRequest request){

        ApiResult apiResult = stationService.deleteOrganization(map,request);
        return apiResult;
    }


    /**
     * 只新增机构  不新增账号关系
     * 只能增加操作人所管理下的机构
     * 分公司级 只能添加 气矿
     * 气矿级  只能添加作业区
     * 作业区级 只能添加场站
     * @param map
     * @param request
     * @return
     */
    @ApiOperation("新增组织机构")
    @PostMapping("/addOrganization")
    @Log(name = "组织结构信息管理 ==> 新增组织机构")
    public ApiResult addOrganization(
            @ApiParam(name = "map", value = "name=机构名称（必传）," +
                    "type=机构类型（必传 1-气矿  2-作业区  3-场站）," +
                    "factoryTypeId=工艺分类Id(新增场站时必传)" +
                    "factoryId=气矿id," +
                    "areaId=作业区id," +
                    "stationId=场站Id（非必传）"
                    ,required = true)
            @RequestBody Map map, HttpServletRequest request){

        ApiResult apiResult = stationService.addOrganization(map,request);
        return apiResult;
    }


    @ApiOperation("查询所有工艺分类")
    @GetMapping("/getFactoryType")
    @Log(name = "组织结构信息管理 ==> 查询所有工艺分类")
    public ApiResult getFactoryType(HttpServletRequest request){

        ApiResult apiResult = stationService.getFactoryType(request);
        return apiResult;
    }



    @ApiOperation("修改组织机构名称")
    @PutMapping("/updateOrganization")
    @Log(name = "组织结构信息管理 ==> 修改组织机构名称")
    public ApiResult updateOrganization(
            @ApiParam(name = "map", value = "name=机构名称（必传）," +
                    "factoryId=气矿id（非必传）," +
                    "areaId=作业区id（非必传）," +
                    "stationId=场站Id（非必传）(三个至少传一个)"
                    ,required = true)
            @RequestBody Map map, HttpServletRequest request){

        ApiResult apiResult = stationService.updateOrganization(map,request);
        return apiResult;
    }



}
