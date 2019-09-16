package com.cj.exclemeasures.controller;

import com.cj.common.entity.Measures;
import com.cj.common.entity.MeasuresLast;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.Pager;
import com.cj.core.aop.Log;
import com.cj.exclemeasures.domain.FindMeasuresByLastYearResp;
import com.cj.exclemeasures.domain.FindMeasuresByThisYearResp;
import com.cj.exclemeasures.domain.UpdateMeasuresByLastYearReq;
import com.cj.exclemeasures.domain.UpdateMeasuresByThisYearReq;
import com.cj.exclemeasures.service.MeasuresService;
import com.google.gson.Gson;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Api(tags = "环境风险防控与应急措施模块: ")
@RestController
@RequestMapping("/api/v1/measures/measures")
public class MeasuresController {

    @Resource
    private MeasuresService measuresService;

    /**
     * 根据年份查询环境风险防控与应急措施信息
     * 
     * @return
     */
    @GetMapping("/MeasuresByThisYear")
    @ApiOperation(value = "查询 本年环境风险防控与应急措施", response = FindMeasuresByThisYearResp.class)
    @ApiResponse(code = 0, message ="不管这个状态",response = Pager.class)
    @Log(name = "环境风险防控与应急措施模块",value = "查询 本年环境风险防控与应急措施")
    @ApiImplicitParam(name = "json",value = "stationId=场站ID,year=年份",required = true,
            defaultValue = "{\"currentPage\":1,\"pageSize\":10,\"parameters\":{\"stationId\":1,\"year\":\"2018\"}}")
    public ApiResult findMeasuresByThisYear(String json,HttpSession session){
        Pager pager1 = new Gson().fromJson(json,Pager.class);

        Pager pager = new Pager();
        pager.setCurrentPage(pager1.getCurrentPage());
        pager.setPageSize(pager1.getPageSize());
        pager.setParameters(pager1.getParameters());
        return measuresService.findMeasuresByThisYear(pager,session);

    }

    @PostMapping("/MeasuresByThisYear")
    @ApiOperation(value = "新增 本年环境风险防控与应急措施")

    @Log(name = "环境风险防控与应急措施模块",value = "新增 本年环境风险防控与应急措施")
    public ApiResult addMeasuresByThisYear(
            @ApiParam(name = "measures",
                    value = "List<Measures>=\n" +
                            "[{\"completionTime\":\"2017-10-23 06:01:02\",\"lastMeasures\":\"上年度制定的环境风险防控措施计划1\",\"lastPersonLiable\":\"正在落实（尚未完成）的责任人1\",\"measuresCompleted\":\"已经完成整改的环境风险防控措施1\",\"measuresContent\":\"正在落实（尚未完成）的措施内容1\",\"noStartPlan\":\"未开始，已纳入后续环境风险防控措施计划1\",\"stationId\":1,\"year\":\"2017-01-01 06:01:02\",\"state\":\"1\"},{\"completionTime\":\"2017-10-23 06:01:02\",\"lastMeasures\":\"上年度制定的环境风险防控措施计划2\",\"lastPersonLiable\":\"正在落实（尚未完成）的责任人2\",\"measuresCompleted\":\"已经完成整改的环境风险防控措施2\",\"measuresContent\":\"正在落实（尚未完成）的措施内容2\",\"noStartPlan\":\"未开始，已纳入后续环境风险防控措施计划2\",\"stationId\":1,\"year\":\"2017-01-01 06:01:02\",\"state\":\"1\"}]",
                    required = true)
            @RequestBody List<Measures> measures, HttpServletRequest request){

        return measuresService.addMeasuresByThisYear(measures,request);

    }

    @PutMapping("/MeasuresByThisYear")
    @ApiOperation("编辑 本年环境风险防控与应急措施")
    @Log(name = "环境风险防控与应急措施模块",value = "编辑 本年环境风险防控与应急措施")
    public ApiResult updateMeasuresByThisYear(
            @ApiParam(name = "measures",value = "map（oldMeasures=对旧数据进行操作，带ID。newMeasures=新增的数据，无ID）" +
                    "==\n{\"oldMeasures\":[{\"measuresId\":23,\"stationId\":1,\"year\":1514736000000,\"existingMeasures\":\"现有措施1改\",\"proposedRectification\":\"本年整改措施1改\",\"plannedTime\":1540137600000,\"department\":\"本年整改责任部门1改\",\"personLiable\":\"本年整改责任人1改\",\"state\":\"0\"},{\"measuresId\":24,\"stationId\":1,\"year\":1514736000000,\"existingMeasures\":\"现有措施2\",\"proposedRectification\":\"本年整改措施2\",\"plannedTime\":1540137600000,\"department\":\"本年整改责任部门2\",\"personLiable\":\"本年整改责任人2\",\"state\":\"0\"}],\"newMeasures\":[{\"stationId\":1,\"year\":1514736000000,\"existingMeasures\":\"现有措施1增1\",\"proposedRectification\":\"本年整改措施1增1\",\"plannedTime\":1540137600000,\"department\":\"本年整改责任部门1增1\",\"personLiable\":\"本年整改责任人1增1\"},{\"stationId\":1,\"year\":1514736000000,\"existingMeasures\":\"现有措施2增2\",\"proposedRectification\":\"本年整改措施2增2\",\"plannedTime\":1540137600000,\"department\":\"本年整改责任部门2增2\",\"personLiable\":\"本年整改责任人2增2\"}]}",
                    required = true)
            @RequestBody UpdateMeasuresByThisYearReq updateMeasuresByThisYearReq, HttpServletRequest request){

        return measuresService.updateMeasuresByThisYear(updateMeasuresByThisYearReq,request);

    }

    //==============================================上年===========================================================

    /**
     * 根据年份查询环境风险防控与应急措施信息（
     *
     * @return
     */
    @GetMapping("/MeasuresByLastYear")
    @ApiOperation(value = "查询 上年环境风险防控与应急措施",response = FindMeasuresByLastYearResp.class)
    @Log(name = "环境风险防控与应急措施模块",value = "查询 上年环境风险防控与应急措施")
    @ApiImplicitParam(name = "json",value = "stationId=场站ID,year=年份",required = true,
            defaultValue = "{\"currentPage\":1,\"pageSize\":10,\"parameters\":{\"stationId\":1,\"year\":\"2017\"}}")
    public ApiResult findMeasuresByLastYear(String json, HttpSession session ){
        Pager pager1 = new Gson().fromJson(json,Pager.class);

        Pager pager = new Pager();
        pager.setCurrentPage(pager1.getCurrentPage());
        pager.setPageSize(pager1.getPageSize());
        pager.setParameters(pager1.getParameters());
        return measuresService.findMeasuresByLastYear(pager,session);

    }


    @PostMapping("/MeasuresByLastYear")
    @ApiOperation("新增 上年环境风险防控与应急措施")
    @Log(name = "环境风险防控与应急措施模块",value = "新增 上年环境风险防控与应急措施")
    public ApiResult addMeasuresByLastYear(
            @ApiParam(name = "measuresLasts",value = "List<MeasuresLast>==\n" +
                    "[{\"completionTime\":\"2017-10-23T06:01:02.307Z\",\"lastMeasures\":\"上年度制定的环境风险防控措施计划1\",\"lastPersonLiable\":\"正在落实（尚未完成）的责任人1\",\"measuresCompleted\":\"已经完成整改的环境风险防控措施1\",\"measuresContent\":\"正在落实（尚未完成）的措施内容1\",\"noStartPlan\":\"未开始，已纳入后续环境风险防控措施计划1\",\"stationId\":1,\"year\":\"2017-01-01T06:01:02.307Z\",\"state\":\"1\"},{\"completionTime\":\"2017-10-23T06:01:02.307Z\",\"lastMeasures\":\"上年度制定的环境风险防控措施计划2\",\"lastPersonLiable\":\"正在落实（尚未完成）的责任人2\",\"measuresCompleted\":\"已经完成整改的环境风险防控措施2\",\"measuresContent\":\"正在落实（尚未完成）的措施内容2\",\"noStartPlan\":\"未开始，已纳入后续环境风险防控措施计划2\",\"stationId\":1,\"year\":\"2017-01-01T06:01:02.307Z\",\"state\":\"1\"}]",
                    required = true)
            @RequestBody List<MeasuresLast> measuresLasts, HttpServletRequest request){

        return measuresService.addMeasuresByLastYear(measuresLasts,request);
    }



    @PutMapping("/MeasuresByLastYear")
    @ApiOperation("编辑 上年环境风险防控与应急措施")
    @Log(name = "环境风险防控与应急措施模块",value = "编辑 上年环境风险防控与应急措施")
    public ApiResult updateMeasuresByLastYear(
            @ApiParam(name = "measuresLasts",value = "map（oldMeasuresLasts=对旧数据进行操作，带ID。newMeasuresLasts=新增的数据，无ID）\n" +
                    "{\"oldMeasuresLasts\":[{\"lastMeasuresId\":10,\"completionTime\":\"2017-10-23T06:01:02.307Z\",\"lastMeasures\":\"上年度制定的环境风险防控措施计划1\",\"lastPersonLiable\":\"正在落实（尚未完成）的责任人1\",\"measuresCompleted\":\"已经完成整改的环境风险防控措施1\",\"measuresContent\":\"正在落实（尚未完成）的措施内容1\",\"noStartPlan\":\"未开始，已纳入后续环境风险防控措施计划1\",\"stationId\":1,\"year\":\"2017-01-01T06:01:02.307Z\",\"state\":\"0\"},{\"lastMeasuresId\":9,\"completionTime\":\"2017-10-23T06:01:02.307Z\",\"lastMeasures\":\"上年度制定的环境风险防控措施计划2\",\"lastPersonLiable\":\"正在落实（尚未完成）的责任人2\",\"measuresCompleted\":\"已经完成整改的环境风险防控措施2\",\"measuresContent\":\"正在落实（尚未完成）的措施内容2\",\"noStartPlan\":\"未开始，已纳入后续环境风险防控措施计划2\",\"stationId\":1,\"year\":\"2017-01-01T06:01:02.307Z\",\"state\":\"0\"}],\"newMeasuresLasts\":[{\"completionTime\":\"2017-10-23T06:01:02.307Z\",\"lastMeasures\":\"上年度制定的环境风险防控措施计划1改1\",\"lastPersonLiable\":\"正在落实（尚未完成）的责任人1改1\",\"measuresCompleted\":\"已经完成整改的环境风险防控措施1改1\",\"measuresContent\":\"正在落实（尚未完成）的措施内容1改1\",\"noStartPlan\":\"未开始，已纳入后续环境风险防控措施计划1改1\",\"stationId\":1,\"year\":\"2017-01-01T06:01:02.307Z\",\"state\":\"1\"},{\"completionTime\":\"2017-10-23T06:01:02.307Z\",\"lastMeasures\":\"上年度制定的环境风险防控措施计划2改2\",\"lastPersonLiable\":\"正在落实（尚未完成）的责任人2改2\",\"measuresCompleted\":\"已经完成整改的环境风险防控措施2改2\",\"measuresContent\":\"正在落实（尚未完成）的措施内容2改2\",\"noStartPlan\":\"未开始，已纳入后续环境风险防控措施计划2改2\",\"stationId\":1,\"year\":\"2017-01-01T06:01:02.307Z\",\"state\":\"1\"}]}",
                    required = true)
            @RequestBody UpdateMeasuresByLastYearReq updateMeasuresByLastYearReq, HttpServletRequest request){

        return measuresService.updateMeasuresByLastYear(updateMeasuresByLastYearReq,request);
    }

    //==========================================导出excle================================


    @GetMapping("/exportMeasures")
    @ApiOperation("导出 环境风险防控与应急措施")
    @Log(name = "环境风险防控与应急措施模块",value = "导出 环境风险防控与应急措施")
    @ApiImplicitParam(name = "json",value = "stationId=场站ID,year=本年份",required = true,
            defaultValue = "{\"currentPage\":1,\"pageSize\":100,\"parameters\":{\"stationId\":1,\"year\":\"2018\"}}")
    public void exportMeasures(String json, HttpServletResponse response) throws Exception {

        Pager pager1 = new Gson().fromJson(json,Pager.class);

        Pager pager = new Pager();
        pager.setCurrentPage(pager1.getCurrentPage());
        pager.setPageSize(pager1.getPageSize());
        pager.setParameters(pager1.getParameters());

        measuresService.exportMeasures(pager,response);

    }

}
