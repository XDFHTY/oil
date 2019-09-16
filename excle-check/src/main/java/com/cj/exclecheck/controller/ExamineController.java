package com.cj.exclecheck.controller;

import com.cj.core.domain.ApiResult;
import com.cj.core.aop.Log;
import com.cj.exclecheck.service.ExamineService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 审批流程模块
 * Created by XD on 2018/10/23.
 */
@RestController
@RequestMapping("api/v1/approval/examine")
@Api(tags = "审批流程管理 ==> 审核管理")
public class ExamineController {

    @Autowired
    private ExamineService examineService;

    @ApiOperation("提交审核")
    @PutMapping("/submit")
    @Log(name = "审核管理 ==> 提交审核")
    public ApiResult submit(
            @ApiParam(name = "map", value = "参数  year=年份（必传）," +
                    "stationId=场站id（非必传，场站提交审核则必传）"
                    ,required = true)
            @RequestBody Map map, HttpServletRequest request){

        ApiResult apiResult = this.examineService.submit(map,request);
        return apiResult;
    }



    @ApiOperation("审核 通过或驳回")
    @PutMapping("/examine")
    @Log(name = "审核管理 ==> 通过或驳回")
    public ApiResult examine(
            @ApiParam(name = "map", value = "参数  year=年份（必传）," +
                    "stationId=场站id（作业区审核传入  否则为null）" +
                    "taskAreaId=作业区id（气矿审核传入  否则为null）" +
                    "result=审核结果(1-通过  2-驳回) " +
                    "reason=驳回原因（审核结果为 驳回时 必传）"
                    ,required = true)
            @RequestBody Map map, HttpServletRequest request){

        ApiResult apiResult = this.examineService.examine(map,request);
        return apiResult;
    }


    @ApiOperation("查询审核结果")
    @GetMapping("/examineResult")
    @Log(name = "审核管理 ==> 查询审核结果")
    @ApiImplicitParam(name = "year",value = "",required = true)
    public ApiResult examineResult(
             String year, HttpServletRequest request){

        ApiResult apiResult = this.examineService.examineResult(year,request);
        return apiResult;
    }


    /**
     * 查询待审核
     * 场站id 不为null  则查询 作业区未审核的
     * 作业区id不为null  则查询 气矿未审核的
     */
    @ApiOperation("查询待审核")
    @GetMapping("/unaudited")
    @Log(name = "审核管理 ==> 查询待审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年份", required = true),
            @ApiImplicitParam(name = "currentPage", value = "页码",required = true)
    })

    public ApiResult unaudited(
            String year, @RequestParam(defaultValue = "1") int currentPage,HttpServletRequest request){
        ApiResult apiResult = this.examineService.unaudited(year,request,currentPage);
        return apiResult;
    }




    /**
     * 根据场站id
     * 如果是作业区级  则查询场站下所有的表
     * 如果是场站级 则查询场站填写的表
     */
    @ApiOperation("查询提交信息详情")
    @GetMapping("/info")
    @Log(name = "审核管理 ==> 查询提交信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年份", required = true),
            @ApiImplicitParam(name = "stationId", value = "场站id" ,required = true)
    })

    public ApiResult info(
            String year, Long stationId,HttpServletRequest request){
        ApiResult apiResult = this.examineService.getInfo(year,stationId,request);
        return apiResult;
    }



}
