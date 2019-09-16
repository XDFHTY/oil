package com.cj.analysis.controller;


import com.cj.analysis.server.EnvironmentLeaderServer;
import com.cj.analysis.vo.VoLedger;
import com.cj.common.entity.TaskArea;
import com.cj.core.domain.ApiResult;
import com.cj.core.aop.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Api(tags = "数据提取及分析：风险台账")
@RestController
@RequestMapping("/api/v1/environment")
public class EnvironmentLedgerController {

    /**
     * 获取台账数据
     */
    @Autowired
    EnvironmentLeaderServer environmentLeaderServer;

    @PostMapping("/ledger")
    @ApiOperation(value = "根据条件查询查询台账信息",response = TaskArea.class)
    @Log(name = "数据提取及分析：根据条件查询查询台账信息",value = "根据条件查询查询台账信息")
    public ApiResult getEnvironmentLedger(@RequestBody VoLedger query, HttpServletRequest request){
        ApiResult apiResult=environmentLeaderServer.getEnvironmentLeader(query,request);
        return apiResult;
    }



    @PostMapping("/ledger/excel")
    @ApiOperation(value = "根据条件查询查询台账信息",response = TaskArea.class)
    @Log(name = "数据提取及分析：根据条件查询查询台账信息",value = "根据条件查询查询台账信息")
    public ApiResult getExcelEnvironmentLedger(@RequestBody VoLedger query, HttpServletResponse response, HttpServletRequest request){
        response.setHeader("content-Type", "application/vnd.ms-excel");
        //下载文件的默认名字
        try {
            response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("AccountFormat.xlsx", "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ApiResult apiResult =environmentLeaderServer.getExcelEnvironmentLeader(query,response,request);
        return apiResult;
    }
}
