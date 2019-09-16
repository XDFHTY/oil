package com.cj.organization.controller;

import com.cj.core.domain.ApiResult;
import com.cj.core.aop.Log;
import com.cj.organization.service.ExcelImportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Excel导入
 * Created by XD on 2018/10/10.
 */
@RestController
@RequestMapping("api/v1/organization/info")
@Api(tags = "组织结构信息管理 ==> 组织结构信息管理")
public class ExcelImportController {

    @Autowired
    private ExcelImportService excelImportService;


    /**
     *  功能描述：Excel导入组织机构基本信息(上传文件，文件解析，数据的插入)
     *  参数：Excel文件
     *  返回：导入成功或失败
     */
    @ApiOperation("导入组织机构基本信息")
    @Log(name = "组织结构信息管理 ==> 导入组织机构基本信息")
    @ApiParam(value = "组织机构基本信息导入",required = true)
    @PostMapping("/importInfo")
    public ApiResult importInfo(MultipartFile file, HttpServletRequest request){

        //返回对象
        ApiResult apiResult;

        if(file == null) {
            apiResult = ApiResult.FAIL(); //没有数据
            apiResult.setMsg("没有数据");
            return apiResult;
        }
        try{
            //获取文件流
            InputStream in = file.getInputStream();
            apiResult = this.excelImportService.importInfo(file, in);
        }catch (Exception e){ //异常处理
            apiResult = ApiResult.FAIL();
            e.printStackTrace();
        }
        return apiResult;
    }



    @ApiOperation(value = "导出 场站，生成需要填写账号格式表", notes = "成功/失败")
    @GetMapping("/organizationExport")
    @Log(name = "excel管理 ==> 导出场站，生成表格")
    public ApiResult organizationExport( HttpServletResponse response, HttpServletRequest request){
        //用什么软件打开
        response.setHeader("content-Type", "application/vnd.ms-excel");
        //下载文件的默认名字
        try {
            response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("AccountFormat.xlsx", "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ApiResult apiResult = this.excelImportService.organizationExport(response,request);
        return apiResult;
    }

    @ApiOperation(value = "导出 作业区，生成需要填写账号格式表", notes = "成功/失败")
    @GetMapping("/taskAreaExport")
    @Log(name = "excel管理 ==> 导出作业区，生成表格")
    public ApiResult taskAreaExport( HttpServletResponse response, HttpServletRequest request){
        //用什么软件打开
        response.setHeader("content-Type", "application/vnd.ms-excel");
        //下载文件的默认名字
        try {
            response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("AccountFormat.xlsx", "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ApiResult apiResult = this.excelImportService.taskAreaExport(response,request);
        return apiResult;
    }

    @ApiOperation(value = "导出 厂、矿，生成需要填写账号格式表", notes = "成功/失败")
    @GetMapping("/factoryExport")
    @Log(name = "excel管理 ==> 导出厂、矿，生成表格")
    public ApiResult factoryExport( HttpServletResponse response, HttpServletRequest request){
        //用什么软件打开
        response.setHeader("content-Type", "application/vnd.ms-excel");
        //下载文件的默认名字
        try {
            response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("AccountFormat.xlsx", "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ApiResult apiResult = this.excelImportService.factoryExport(response,request);
        return apiResult;
    }




    /**
     *  功能描述：导入气矿和账号的关系表格
     *  参数：Excel文件
     *  返回：导入成功或失败
     */
    @ApiOperation("导入气矿和账号的关系表格")
    @Log(name = "组织结构信息管理 ==> 导入气矿和账号的关系表格")
    @ApiParam(value = "导入气矿和账号的关系表格",required = true)
    @PostMapping("/importFactoryAdmin")
    public ApiResult importFactoryAdmin(MultipartFile file, HttpServletRequest request){

        //返回对象
        ApiResult apiResult;

        if(file == null) {
            apiResult = ApiResult.FAIL(); //没有数据
            apiResult.setMsg("没有数据");
            return apiResult;
        }
        try{
            //获取文件流
            InputStream in = file.getInputStream();
            apiResult = this.excelImportService.importFactoryAdmin(file, in);
        }catch (Exception e){ //异常处理
            apiResult = ApiResult.FAIL();
            e.printStackTrace();
        }
        return apiResult;
    }



    /**
     *  功能描述：导入作业区和账号的关系表格
     *  参数：Excel文件
     *  返回：导入成功或失败
     */
    @ApiOperation("导入作业区和账号的关系表格")
    @Log(name = "组织结构信息管理 ==> 导入作业区和账号的关系表格")
    @ApiParam(value = "导入作业区和账号的关系表格",required = true)
    @PostMapping("/importAreaAdmin")
    public ApiResult importAreaAdmin(MultipartFile file, HttpServletRequest request){

        //返回对象
        ApiResult apiResult;

        if(file == null) {
            apiResult = ApiResult.FAIL(); //没有数据
            apiResult.setMsg("没有数据");
            return apiResult;
        }
        try{
            //获取文件流
            InputStream in = file.getInputStream();
            apiResult = this.excelImportService.importAreaAdmin(file, in);
        }catch (Exception e){ //异常处理
            apiResult = ApiResult.FAIL();
            e.printStackTrace();
        }
        return apiResult;
    }



    /**
     *  功能描述：导入场站 和账号的关系表格
     *  参数：Excel文件
     *  返回：导入成功或失败
     */
    @ApiOperation("导入场站 和账号的关系表格")
    @Log(name = "组织结构信息管理 ==> 导入场站 和账号的关系表格")
    @ApiParam(value = "导入场站 和账号的关系表格",required = true)
    @PostMapping("/importStationAdmin")
    public ApiResult importStationAdmin(MultipartFile file, HttpServletRequest request){

        //返回对象
        ApiResult apiResult;

        if(file == null) {
            apiResult = ApiResult.FAIL(); //没有数据
            apiResult.setMsg("没有数据");
            return apiResult;
        }
        try{
            //获取文件流
            InputStream in = file.getInputStream();
            apiResult = this.excelImportService.importStationAdmin(file, in);
        }catch (Exception e){ //异常处理
            apiResult = ApiResult.FAIL();
            e.printStackTrace();
        }
        return apiResult;
    }

}
