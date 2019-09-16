package com.cj.exclematerial.controller;

import com.cj.common.entity.Environmental;
import com.cj.core.domain.ApiResult;
import com.cj.core.aop.Log;
import com.cj.exclematerial.annotation.FileName;
import com.cj.exclematerial.serveice.EnvironmentalService;
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

@Api(tags = "应急物资配备及预案备案情况：环境机构")
@RequestMapping("/api/v1/emergency")
@RestController
public class EnvironmentalController {

    @Autowired
    EnvironmentalService environmentalServer;
    /**
     * 根据条件查询环境机构信息
     * @param param 查询条件
     */
    @ApiOperation(value = "查询环境机构信息",response = Environmental.class)
    @GetMapping("/environmental")
    @Log(name = "应急物资配备及预案备案情况：环境机构",value = "查询环境机构信息")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query",name = "factoryId",value = "厂、矿ID",
            defaultValue = "1"),
            @ApiImplicitParam(paramType = "query",name = "datetime",value = "时间(时间格式yyyy-MM-dd)",
                    defaultValue = "2018-9-10")
    })
    public ApiResult getEnvironmentalByQuery(@RequestParam(required = false) Map<String, Object> param){
        ApiResult apiResult=environmentalServer.findEnvironmental(param);
        return apiResult;
    }

    /**
     * 添加环境机构对信息
     * @param environmental 环境机构信息
     */
    @ApiOperation(value = "添加环境机构信息",response = ApiResult.class)
    @PostMapping("/environmental")
    @Log(name = "应急物资配备及预案备案情况：环境机构",value = "添加环境机构信息")
    public ApiResult addEnvironmental(
            @ApiParam(value = "factoryId=厂、矿ID、environmentalName=环境机构名称、environmentalServer=环境机构的服务对象、environmentalAddress=环境机构地址、environmentalLinkman=环境机构联系人、" +
                    "environmentalLinkmanPhone=环境机构"+
                    "\n{\n" +
                    "  \"factoryId\": 1,\n" +
                    "  \"environmentalName\": \"1号环境机构\",\n" +
                    "  \"environmentalAddress\": \"1号环境机构地址\",\n" +
                    "  \"environmentalServer\": \"1号服务对象\",\n" +
                    "  \"environmentalLinkman\": \"1号环境机构联系人\",\n" +
                    "  \"environmentalLinkmanPhone\": 13183897579\n" +
                    "}" +
                    "")
            @Validated @RequestBody Environmental environmental,BindingResult bindingResult){
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
        ApiResult apiResult=environmentalServer.addEnvironmental(environmental);
        return apiResult;
    }

    /**
     * 修改环境机构信息
     * @param environmental 环境机构信息
     */
    @ApiOperation(value = "修改环境机构信息",response = ApiResult.class)
    @PutMapping("/environmental")
    @Log(name = "环境机构",value = "修改环境机构信息")
    public ApiResult updateEnvironmental(
            @ApiParam(value = "factoryId=厂、矿ID、environmentalName=修改后环境机构名称、environmentalServer=修改后环境机构的服务对象、environmentalAddress=修改后环境机构地址、environmentalLinkman=修改后环境机构联系人、" +
                    "environmentalLinkmanPhone=环境机构"+
                    "\n{\n" +
                    "  \"factoryId\": 1,\n" +
                    "  \"environmentalId\": 1,\n" +
                    "  \"environmentalName\": \"1号环境机构\",\n" +
                    "  \"environmentalAddress\": \"1号环境机构地址\",\n" +
                    "  \"environmentalServer\": \"1号服务对象\",\n" +
                    "  \"environmentalLinkman\": \"1号环境机构联系人\",\n" +
                    "  \"environmentalLinkmanPhone\": 13183897579\n" +
                    "}" +
                    "")
            @Validated @RequestBody Environmental environmental,BindingResult bindingResult){
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
        ApiResult apiResult=environmentalServer.updateEnvironmental(environmental);
        return apiResult;
    }

    /**
     * 获取Excel
     */
    @GetMapping("/environmental/excel")
    @ApiOperation(value = "按条件获取环境监测机构信息的Excel",response = Environmental.class)
    @Log(name = "应急物资配备及预案备案情况:环境机构",value = "按条件获取环境监测机构信息的Excel")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "factoryId",value = "厂、矿ID",defaultValue = "1"),
            @ApiImplicitParam(paramType = "query",name = "datetime",value = "时间(时间格式yyyy-MM-dd)",defaultValue = "2018-9-10")
    })
    public void getEnvironmentalExcelByQuery(@RequestParam(required = false) Map<String, Object> param, HttpServletResponse response) throws IOException, ClassNotFoundException {
        HSSFWorkbook wb=environmentalServer.getEnvironmentalExcelByQuery(param);
        //输出Excel文件
        String fileName = URLEncoder.encode("环境监测机构分布表", StandardCharsets.UTF_8.toString());
        OutputStream out = response.getOutputStream();
        response.setHeader("content-Type", "application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "; filename*=utf-8''" + fileName);
        response.setCharacterEncoding("UTF-8");
        wb.write(out);
        out.flush();
        out.close();
    }
    @DeleteMapping("/environmental")
    public ApiResult deleteEnvironmental(@RequestParam Long id){
        ApiResult apiResult=environmentalServer.deleteEnvironmental(id);
        return apiResult;
    }
}
