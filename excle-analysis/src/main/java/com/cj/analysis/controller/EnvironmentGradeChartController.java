package com.cj.analysis.controller;

import com.cj.analysis.pojo.Result;
import com.cj.analysis.server.EnvironmentGradeChartServer;
import com.cj.analysis.vo.VoChart;
import com.cj.core.domain.ApiResult;
import com.cj.core.aop.Log;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = "数据提取及分析：数据成图")
@RestController
@RequestMapping("/api/v1/environment")
public class EnvironmentGradeChartController {
    @Autowired
    EnvironmentGradeChartServer environmentGradeChartServer;

    /**
     * 查询作业区的统计图
     * @param param 查询条件(时间，气矿Id，工作区Id)
     */
    @ApiOperation(value = "查询作业区的统计图",response = Result.class)
    @PostMapping("/chart/taskArea")
    @Log(name = "数据提取及分析：数据成图",value = "根据条件查询环境评估结果")
    public ApiResult getEnvironmentGradeByQuery(@RequestBody VoChart param){
        ApiResult apiResult=environmentGradeChartServer.findTaskAreaChart(param);
        return apiResult;
    }
}
