package com.cj.exclematerial.controller;

import com.cj.common.entity.Brigade;
import com.cj.core.domain.ApiResult;
import com.cj.core.aop.Log;
import com.cj.exclematerial.annotation.FileName;
import com.cj.exclematerial.serveice.BrigadeService;
import io.swagger.annotations.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Api(tags = "应急物资配备及预案备案情况：专职消防队")
@RequestMapping("/api/v1/emergency")
@RestController
public class BrigadeController {

    @Autowired
    BrigadeService brigadeServer;
    /**
     * 根据条件查询消防队信息
     * @param param 查询条件
     */
    @ApiOperation(value = "查询消防队信息",response =Brigade.class )
    @GetMapping("/brigade")
    @Log(name = "应急物资配备及预案备案情况：专职消防队",value = "查询消防队信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "factoryId",required = true,value = "气矿Id",defaultValue = "1"),
            @ApiImplicitParam(paramType = "query",name = "datetime",value = "时间(时间格式yyyy-MM-dd)",required = true,defaultValue = "2018-9-10")
    })
    public ApiResult getBrigadeByQuery(
            @RequestParam(required = false) Map<String, Object> param){
        ApiResult apiResult=brigadeServer.findBrigade(param);
        return apiResult;
    }

    /**
     * 添加消防对信息
     * @param brigade 消防队信息
     */
    @ApiOperation(value = "添加消防对信息",response = ApiResult.class)
    @PostMapping("/brigade")
    @Log(name = "应急物资配备及预案备案情况:专职消防队",value = "添加消防队信息")
    public ApiResult addBrigade(
            @ApiParam(value = "factoryId=消防队所属气矿的ID、brigade=消防队名称、brigadePerson=消防队负责人姓名、brigadePersonTelphone=负责人办公电话、brigadePersonPhone=负责人联系方式、" +
                    "\n{\n" +
                    "  \"factoryId\": 1,\n" +
                    "  \"brigade\": \"测试消防队\",\n" +
                    "  \"brigadePerson\": \"测试消防队负责人姓名\",\n" +
                    "  \"brigadePersonTelphone\": 13183897579,\n" +
                    "  \"brigadePersonPhone\": 1235876\n" +
                    "}" +
                    "")
            @Validated @RequestBody Brigade brigade,BindingResult bindingResult, HttpSession httpSession ){

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
        brigade.setTableName(httpSession.getAttribute("name").toString());
        ApiResult apiResult=brigadeServer.addBrigade(brigade);
        return apiResult;
    }

    /**
     * 修改消防队信息
     * @param brigade 消防队信息
     */
    @ApiOperation(value = "修改消防对信息",response = ApiResult.class)
    @PutMapping("/brigade")
    @Log(name = "应急物资配备及预案备案情况:专职消防队",value = "修改消防队信息")
    public ApiResult updateBrigade(
            @ApiParam(value = "factoryId=消防队所属气矿的ID、brigade=消防队名称、brigadePerson=消防队负责人姓名、brigadePersonTelphone=负责人办公电话、brigadePersonPhone=负责人联系方式、" +
                    "\n{\n" +
                    "  \"brigadeId\": 1,\n" +
                    "  \"factoryId\": 1,\n" +
                    "  \"brigade\": \"修改消防队\",\n" +
                    "  \"brigadePerson\": \"修改后的消防队信息\",\n" +
                    "  \"brigadePersonTelphone\": 13183897579,\n" +
                    "  \"brigadePersonPhone\": 1235876\n" +
                    "}" +
                    "")
            @Validated @RequestBody Brigade brigade,BindingResult bindingResult){
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
        ApiResult apiResult=brigadeServer.updateBrigade(brigade);
        return apiResult;
    }
    /**
     * 获取Excel
     */
    @GetMapping("/brigade/excel")
    @ApiOperation(value = "按条件获取消防站信息的Excel",response = Brigade.class)
    @Log(name = "应急物资配备及预案备案情况:专职消防队",value = "按条件获取消防站信息的Excel")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "factoryId",value = "气矿Id",defaultValue = "1"),
            @ApiImplicitParam(paramType = "query",name = "datetime",value = "时间(时间格式yyyy-MM-dd)",defaultValue = "2018-9-10")
    })
    public void getBrigadeExcelByQuery(@RequestParam(required = false) Map<String, Object> param, HttpServletResponse response) throws IOException, ClassNotFoundException {
        HSSFWorkbook wb=brigadeServer.getBrigadeExcelByQuery(param);
        //输出Excel文件
        String fileName = URLEncoder.encode("消防队分布信息", StandardCharsets.UTF_8.toString());
        OutputStream out = response.getOutputStream();
        response.setHeader("content-Type", "application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "; filename*=utf-8''" + fileName);
        response.setCharacterEncoding("UTF-8");
        wb.write(out);
        out.flush();
        out.close();
    }

    @DeleteMapping("/brigade")
    public ApiResult deleteBrigade(@RequestParam Long id){
        ApiResult apiResult=brigadeServer.deleteBrigadeById(id);
        return apiResult;
    }

}
