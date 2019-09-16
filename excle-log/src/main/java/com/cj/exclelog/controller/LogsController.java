package com.cj.exclelog.controller;

import com.cj.common.entity.LogCheck;
import com.cj.common.utils.excle.ImportExeclUtil;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.Pager;
import com.cj.core.aop.Log;
import com.cj.exclelog.service.LogsService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RequestMapping("/api/v1/log/checklog")
@Api(tags = "日志模块: ")
@RestController
public class LogsController {

    @Resource
    private LogsService logsService;

    /**
     * 分页、条件查询审核日志
     * @return
     */
    @GetMapping("/logs")
    @ApiOperation("查询审核日志")
    @Log(name = "日志模块:",value = "查询操作日志")
    @ApiImplicitParam(name = "json",value = "",required = true,
            defaultValue = "{\"currentPage\":1,\"pageSize\":10," +
                    "\"parameters\":{\"factoryId\":\"1\",\"taskAreaId\":\"1\",\"stationId\":\"1\",\"year\":\"2018\",\"fullName\":\"李四\"}}")
    public ApiResult findLog(String json){
        Pager pager1 = new Gson().fromJson(json,Pager.class);

        Pager pager = new Pager();
        pager.setCurrentPage(pager1.getCurrentPage());
        pager.setPageSize(pager1.getPageSize());
        pager.setParameters(pager1.getParameters());

        pager = logsService.findLog(pager);

        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(pager);
        return apiResult;
    }


    @PostMapping("/logs")
    @ApiOperation("新增审核日志")
    @Log(name = "日志模块:",value = "新增审核日志")
    public ApiResult addLog(@ApiParam(name = "logCheck",value = "审核日志信息",required = true)
                      @RequestBody LogCheck logCheck){
        ApiResult apiResult = null;

        logCheck.setYear(ImportExeclUtil.DateUtil.strToDate(logCheck.getDate(), ImportExeclUtil.DateUtil.YYYY));

        int i = logsService.addLog(logCheck);

        if (i>0){

            apiResult = ApiResult.SUCCESS();
        }else {
            apiResult = ApiResult.FAIL();
        }

        return apiResult;
    }


}
