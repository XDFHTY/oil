package com.cj.exclematerial.controller;

import com.cj.common.entity.Overhaul;
import com.cj.core.domain.ApiResult;
import com.cj.core.aop.Log;
import com.cj.exclematerial.annotation.FileName;
import com.cj.exclematerial.serveice.OverhaulService;
import io.swagger.annotations.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/emergency")
@Api(tags = "应急物资配备及预案备案情况：检修队分布")
public class OverhaulController {
    @Autowired
    OverhaulService overhaulServer;
    /**
     * 根据条件查询检修队信息
     * @param param 查询条件
     */
    @ApiOperation(value = "根据条件查询检修队信息",response = Overhaul.class)
    @GetMapping("/overhaul")
    @Log(name = "检修队",value = "查询检修队信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "factoryId",value = "厂、矿ID",defaultValue = "1"),
            @ApiImplicitParam(paramType = "query",name = "datetime",value = "时间(时间格式yyyy-MM-dd)",defaultValue = "2018-9-10")
    })
    public ApiResult getOverhaulByQuery(@RequestParam(required = false) Map<String, Object> param){
        ApiResult apiResult=overhaulServer.findOverhaulByQuery(param);
        return apiResult;
    }

    /**
     * 添加检修队信息
     * @param overhaul 检修队信息
     */
    @ApiOperation(value = "添加检修队信息",response = ApiResult.class)
    @PostMapping("/overhaul")
    @Log(name = "检修队",value = "添加检修队信息")
    public ApiResult addOverhaul(
            @ApiParam(value = "overhaul=检修队名称、overhaulPerson=检修队负责人姓名、overhaulPersonTelphone=负责人办公室电话、overhaulPersonPhone=负责人联系方式、factoryId=所属气矿ID" +
                    "\n{\n" +
                    "  \"factoryId\": 1,\n" +
                    "  \"overhaul\": \"检修队的名称\",\n" +
                    "  \"overhaulPerson\": \"检修队联系人姓名\",\n" +
                    "  \"overhaulPersonTelphone\": 13183897579,\n" +
                    "  \"overhaulPersonPhone\": 13183897579\n" +
                    "}" +
                    "")
            @Validated @RequestBody Overhaul overhaul,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            String validated="";
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                //...
                validated+=fieldError.getDefaultMessage()+",";
            }
            ApiResult apiResult=ApiResult.FAIL();
            apiResult.setMsg(validated.substring(0,validated.length()-1));
            return apiResult;
        }
        ApiResult apiResult=overhaulServer.addOverhaul(overhaul);
        return apiResult;
    }

    /**
     * 修改检修队信息
     * @param overhaul 检修队信息
     */
    @ApiOperation(value = "修改检修队信息",response = ApiResult.class)
    @PutMapping("/overhaul")
    @Log(name = "检修队",value = "修改检修队信息")
    public ApiResult updateOverhaul(
            @ApiParam(value = "overhaul=修改后的检修队名称、overhaulPerson=修改后的检修队负责人姓名、overhaulPersonTelphone=负责人办公室电话、overhaulPersonPhone=负责人联系方式、factoryId=所属气矿ID" +
                    "\n{\n" +
                    "  \"overhaulId\": 1,\n" +
                    "  \"factoryId\": 1,\n" +
                    "  \"overhaul\": \"检修队的修改名称\",\n" +
                    "  \"overhaulPerson\": \"检修队修改联系人姓名\",\n" +
                    "  \"overhaulPersonTelphone\": 13183897579,\n" +
                    "  \"overhaulPersonPhone\": 13183897579\n" +
                    "}" +
                    "")
            @Validated @RequestBody Overhaul overhaul,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            String validated="";
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                //...
                validated+=fieldError.getDefaultMessage()+",";
            }
            ApiResult apiResult=ApiResult.FAIL();
            apiResult.setMsg(validated.substring(0,validated.length()-1));
            return apiResult;
        }
        ApiResult apiResult=overhaulServer.updateOverhaul(overhaul);
        return apiResult;
    }

    /**
     * 获取Excel
     */
    @GetMapping("/overhaul/excel")
    @ApiOperation(value = "按条件获取检修部队信息的Excel",response = Overhaul.class)
    @Log(name = "应急物资配备及预案备案情况:检修部队",value = "按条件获取检修部队机构信息的Excel")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "factoryId",value = "厂、矿ID",defaultValue = "1"),
            @ApiImplicitParam(paramType = "query",name = "datetime",value = "时间(时间格式yyyy-MM-dd)",defaultValue = "2018-9-10")
    })
    public void getOverhaulExcelByQuery(@RequestParam(required = false) Map<String, Object> param, HttpServletResponse response) throws IOException, ClassNotFoundException {
        HSSFWorkbook wb=overhaulServer.getOverhaulExcelByQuery(param);
        //输出Excel文件
        String fileName = URLEncoder.encode("检修部队分布情况", StandardCharsets.UTF_8.toString());
        OutputStream out = response.getOutputStream();
        response.setHeader("content-Type", "application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "; filename*=utf-8''" + fileName);
        response.setCharacterEncoding("UTF-8");
        wb.write(out);
        out.flush();
        out.close();
    }

    @DeleteMapping("/overhaul")
    public ApiResult deleteOverhaul(@RequestParam Long id){
        ApiResult apiResult=overhaulServer.deleteOverhaul(id);
        return apiResult;
    }
}
