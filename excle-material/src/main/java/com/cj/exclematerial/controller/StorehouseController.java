package com.cj.exclematerial.controller;

import com.cj.common.entity.Storehouse;
import com.cj.core.domain.ApiResult;
import com.cj.core.aop.Log;
import com.cj.exclematerial.annotation.FileName;
import com.cj.exclematerial.serveice.StorehouseService;
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

@RequestMapping("/api/v1/emergency")
@Api(tags = "应急物资配备及预案备案情况：应急物资储备库")
@RestController
public class StorehouseController {

    @Autowired
    StorehouseService storehouseServer;
    /**
     * 按条件查询
     * @param param
     */
    @ApiOperation(value = "通过query查询储物库信息",response = Storehouse.class)
    @GetMapping("/storehouse")
    @Log(name = "应急物资储物库",value = "通过query查询储物库信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "factoryId",value = "气矿Id",defaultValue = "1"),
            @ApiImplicitParam(paramType = "query",name = "datetime",value = "时间(时间格式yyyy-MM-dd)",defaultValue = "2018-9-10")
    })
    public ApiResult getStorehousesByFactory(@RequestParam(required = false) Map<String, Object> param){
        ApiResult apiResult=storehouseServer.findStorehouses(param);
        return apiResult;
    }


    /**
     * 添加储物库信息
     * @param storehouse
     */
    @PostMapping("/storehouse")
    @ApiOperation(value = "添加储物库信息",response = ApiResult.class)
    @Log(name = "应急物资储物库",value = "添加储物库信息")
    public ApiResult addStorehouse(

            @ApiParam(value = "factoryId=气矿ID、storehouseName=储备库名称、storehouseLeave=储备库级别、storehouseLinkman=储备库联系人、storehouseLinkmanTelphone=联系人电话、storehouseAddress=储物库地址" +
                    "\n{\n" +
                    "  \"factoryId\": 1,\n" +
                    "  \"storehouseName\": \"1号储备库\",\n" +
                    "  \"storehouseLeave\": \"I\",\n" +
                    "  \"storehouseLinkman\": \"1号联系人\",\n" +
                    "  \"storehouseLinkmanTelphone\": 13183897579,\n" +
                    "  \"storehouseAddress\": \"1号地址\"\n" +
                    "}" +
                    "")
            @Validated @RequestBody Storehouse storehouse,BindingResult bindingResult){
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
        ApiResult apiResult=storehouseServer.addStorehouse(storehouse);
        return apiResult;
    }

    /**
     * 修改储物库信息
     * @param storehouse
     */
    @ApiOperation(value = "修改储物库信息",response = ApiResult.class)
    @Log(name = "应急物资储物库",value = "修改储物库信息")
    @PutMapping("/storehouse")
    public ApiResult updateStorehouse(
            @ApiParam(value = "storehouse_id=储物库ID、factoryId=修改后的气矿ID、storehouseName=修改后的储备库名称、storehouseLeave=修改后的储备库级别、storehouseLinkman=修改后的储备库联系人、storehouseLinkmanTelphone=修改后的联系人电话、storehouseAddress=修改后的储物库地址" +
                    "\n{\n" +
                    "  \"storehouse_id\": 1,\n" +
                    "  \"factoryId\": 1,\n" +
                    "  \"storehouseName\": \"1号储备库\",\n" +
                    "  \"storehouseLeave\": \"I\",\n" +
                    "  \"storehouseLinkman\": \"1号联系人,\"\n" +
                    "  \"storehouseLinkmanTelphone\": 13183897579,\n" +
                    "  \"storehouseAddress\": 1号地址\n" +
                    "}" +
                    "")
            @Validated @RequestBody Storehouse storehouse,BindingResult bindingResult){
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
        ApiResult apiResult=storehouseServer.updateStorehous(storehouse);
        return apiResult;
    }

    /**
     * 获取Excel
     */
    @GetMapping("/storehouse/excel")
    @ApiOperation(value = "导出应急物资储备库Excle",response = Storehouse.class)
    @Log(name = "应急物资配备及预案备案情况:应急物资储物库",value = "导出应急物资储备库Excle")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "factoryId",value = "气矿Id",defaultValue = "1"),
            @ApiImplicitParam(paramType = "query",name = "datetime",value = "时间(时间格式yyyy-MM-dd)",defaultValue = "2018-9-10")
    })
    @ApiResponse(code = 200, message = "Success", response = ApiResult.class)
    public void getStorehouseExcelByQuery(@RequestParam(required = false) Map<String, Object> param, HttpServletResponse response) throws IOException, ClassNotFoundException {
        HSSFWorkbook wb=storehouseServer.getStorehouseExcelByQuery(param);
        //输出Excel文件
        String fileName = URLEncoder.encode("应急物资储备库信息", StandardCharsets.UTF_8.toString());
        OutputStream out = response.getOutputStream();
        response.setHeader("content-Type", "application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "; filename*=utf-8''" + fileName);
        response.setCharacterEncoding("UTF-8");
        wb.write(out);
        out.flush();
        out.close();
    }

    @DeleteMapping("/storehouse")
    public ApiResult deleteStorehouse(@RequestParam Long id){
        ApiResult apiResult=storehouseServer.deleteStorehouse(id);
        return apiResult;
    }

}
