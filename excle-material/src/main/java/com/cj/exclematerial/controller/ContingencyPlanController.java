package com.cj.exclematerial.controller;

import com.cj.common.entity.ReservePlan;
import com.cj.core.domain.ApiResult;
import com.cj.core.aop.Log;
import com.cj.exclematerial.annotation.FileName;
import com.cj.exclematerial.serveice.ContingencyPlanService;
import io.swagger.annotations.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Api(tags = "应急物资配备及预案备案情况：应急预案情况")
@RequestMapping("/api/v1/emergency")
@RestController
public class ContingencyPlanController {

    @Autowired
    ContingencyPlanService contingencyPlanServer;
    /**
     * 根据条件查询应急预案情况信息
     * @param param 查询条件Query
     */
    @ApiOperation(value = "查询应急预案情况",response = ReservePlan.class)
    @GetMapping("/contingencyPlan")
    @Log(name = "应急物资配备及预案备案情况：应急预案情况",value = "查询应急预案情况")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "taskAreaId",value = "工作区ID",defaultValue = "1"),
            @ApiImplicitParam(paramType = "query",name = "datetime",value = "时间(时间格式yyyy-MM-dd)",defaultValue = "2018-9-10")
    })
    public ApiResult getContingencyPlanByQuery(@RequestParam(required = false) Map<String, Object> param){
        ApiResult apiResult=contingencyPlanServer.findContingencyPlan(param);
        return apiResult;
    }

    /**
     * 添加应急预案情况
     * @param reservePlan 应急预案信息
     */
    @ApiOperation(value = "添加应急预案情况",response = ApiResult.class)
    @PostMapping("/contingencyPlan")
    @Log(name = "应急物资配备及预案备案情况:应急预案情况",value = "添加应急预案信息")
    public ApiResult addContingencyPlan(
            @ApiParam(value = "fileRecord=预案文本路径，fileReserve=备案文件路径，record=1或0,（1表示备案，0表示未备案）\n" +
                    "{\n" +
                    "  \"fileRecord\": \"预案文本路径\",\n" +
                    "  \"fileReserve\": \"备案文件路径\",\n" +
                    "  \"record\": \"0\",\n" +
                    "  \"recordReason\": \"未备案原因\",\n" +
                    "  \"taskAreaId\": 1\n" +
                    "}")
            @RequestBody ReservePlan  reservePlan){
        ApiResult apiResult=contingencyPlanServer.addContingencyPlan(reservePlan);
        return apiResult;
    }

    /**
     * 修改应急预案情况
     * @param reservePlan 应急预案情况信息
     */
    @ApiOperation(value = "修改应急预案信息",response = ApiResult.class)
    @PutMapping("/contingencyPlan")
    @Log(name = "应急物资配备及预案备案情况:应急预案情况",value = "修改应急预案信息")
    public ApiResult updateContingencyPlan(
            @ApiParam()
            @RequestBody ReservePlan  reservePlan){
        ApiResult apiResult=contingencyPlanServer.updateContingencyPlan(reservePlan);
        return apiResult;
    }
    /**
     * 获取Excel
     */
    @GetMapping(value = "/contingencyPlan/excel")
    @ApiOperation(value = "按条件获取应急预案信息的Excel",response = ReservePlan.class)
    @Log(name = "应急物资配备及预案备案情况:应急预案情况",value = "按条件获取应急预案信息的Excel")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "taskAreaId",value = "工作区ID",defaultValue = "1"),
            @ApiImplicitParam(paramType = "query",name = "datetime",value = "时间(时间格式yyyy-MM-dd)",defaultValue = "2018-9-10")
    })
    public void getContingencyPlanExcelByQuery(@RequestParam(required = false) Map<String, Object> param, HttpServletResponse response) throws IOException, ClassNotFoundException {
        HSSFWorkbook wb=contingencyPlanServer.getContingencyPlanExcelByQuery(param);
        //输出Excel文件
        String fileName = URLEncoder.encode("应急预案统计表", StandardCharsets.UTF_8.toString());
        OutputStream out = response.getOutputStream();
        response.setHeader("content-Type", "application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "; filename*=utf-8''" + fileName);
        response.setCharacterEncoding("UTF-8");
        wb.write(out);
        out.flush();
        out.close();
    }

    @DeleteMapping("/contingencyPlan")
    public ApiResult deleteContingencyPlan(@RequestParam Long id){
        ApiResult apiResult=contingencyPlanServer.deleteContingencyPlan(id);
        return apiResult;
    }
}
