package com.cj.analysis.controller;

import com.cj.analysis.pojo.FactoryEnvironmentView;
import com.cj.analysis.server.EnvironmentResultServer;
import com.cj.analysis.vo.VoEnvironment;
import com.cj.core.domain.ApiResult;
import com.cj.core.aop.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

@Api(tags = "数据提取及分析：环境风险评估")
@RestController
@RequestMapping("/api/v1/environment")
public class EnvironmentResultController {
    @Autowired
    EnvironmentResultServer environmentResultServer;

    /**
     * 根据条件查询某气矿的评定结果汇总情况
     * @param query 查询条件
     */
    @ApiOperation(value = "根据条件查询环境评估结果(点型)",response = FactoryEnvironmentView.class )
    @PostMapping("/result/factory")
    @Log(name = "数据提取及分析：环境风险评估",value = "根据条件查询环境评估结果")
    public ApiResult getFactoryEnvironmentGradeByQuery(@RequestBody VoEnvironment query){
        if(query.getPageSize()==null){
            query.setPageSize(10);
        }
        if(query.getCurrentPage()==null){
            query.setCurrentPage(0);
        }else {
            query.setCurrentPage(query.getCurrentPage()-1);
        }
        ApiResult apiResult=environmentResultServer.findFactoryEnvironmentGrade(query);
        return apiResult;
    }
    /**
     * 根据条件查询某气矿的评定结果汇总情况
     * @param query 查询条件
     */
    @ApiOperation(value = "根据条件查询环境评估结果(线型)",response = FactoryEnvironmentView.class )
    @PostMapping("/result/pipeline")
    @Log(name = "数据提取及分析：环境风险评估",value = "根据条件查询环境评估结果")
    public ApiResult getPipelineEnvironmentGradeByQuery(@RequestBody VoEnvironment query){
        if(query.getPageSize()==null){
            query.setPageSize(10);
        }
        if(query.getCurrentPage()==null){
            query.setCurrentPage(0);
        }else {
            query.setCurrentPage(query.getCurrentPage()-1);
        }
        ApiResult apiResult=environmentResultServer.getPipelineEnvironmentGradeByQuery(query);
        return apiResult;
    }


    /**
     * 获取Excel
     */
    @PostMapping("/result/factory/excel")
    @ApiOperation("根据条件查询环境评估结果的Excel")
    @Log(name = "数据提取及分析：环境风险评估",value = "根据条件查询环境评估结果的Excel")
    public ApiResult getBrigadeExcelByQuery(@RequestBody VoEnvironment query, HttpServletResponse response)throws IOException {
        HSSFWorkbook wb=environmentResultServer.getFactoryEnvironmentExcelByQuery(query);
        //输出Excel文件
        OutputStream output=response.getOutputStream();
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename="+URLEncoder.encode("环境评估统计表.xls", "utf-8"));
        response.setContentType("application/msexcel");
        wb.write(output);
        output.close();
        return null;
    }

}
