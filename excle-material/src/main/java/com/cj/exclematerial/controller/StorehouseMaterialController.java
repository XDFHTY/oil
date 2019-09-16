package com.cj.exclematerial.controller;

import com.cj.common.entity.StorehouseMaterial;
import com.cj.core.domain.ApiResult;
import com.cj.core.aop.Log;
import com.cj.exclematerial.annotation.FileName;
import com.cj.exclematerial.serveice.StorehouseMaterialService;
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

@RequestMapping("/api/v1/emergency")
@Api(tags = "应急物资配备及预案备案情况：储物库应急物资")
@RestController
public class StorehouseMaterialController {

    @Autowired
    StorehouseMaterialService storehouseMaterialServer;
    /**
     * 通过query获取储物库储物信息信息
     * @param param
     */
    @GetMapping("/storehouseMaterial")
    @ApiOperation(value = "通过query获取储物库储物信息信息",response = StorehouseMaterial.class)
    @Log(name = "储物库物资",value = "通过query获取储物库储物信息信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "factoryId",value = "气矿ID",defaultValue = "1"),
            @ApiImplicitParam(paramType = "query",name = "datetime",value = "时间(时间格式yyyy-MM-dd)",defaultValue = "2018-9-10")
    })
    public ApiResult getStorehouseMaterialsByFactoryID(@RequestParam(required = false) Map<String, Object> param){
        ApiResult apiResult = storehouseMaterialServer.findStorehouseMaterialByQuery(param);
        return apiResult;
    }
    /**
     * 添加储物库储备物资的信息
     * @param storehouseMaterial
     */
    @ApiOperation(value = "添加储物库应急物资的信息",response = ApiResult.class)
    @Log(name = "储物库物资",value = "添加储物库储备物资的信息")
    @PostMapping("/storehouseMaterial")
    public ApiResult addStorehouseMaterial(
            @ApiParam("{\n" +
                    "  \"materialId\": 27,\n" +
                    "  \"materialNum\": 27,\n" +
                    "  \"storehouseId\": 24\n" +
                    "}")
            @RequestBody StorehouseMaterial storehouseMaterial){
        ApiResult apiResult = storehouseMaterialServer.addStorehouseMaterial(storehouseMaterial);
        return apiResult;
    }

    /**
     * 修改储物库储备物资的信息
     * @param storehouseMaterial
     */
    @ApiOperation(value = "修改储物库信息",response = ApiResult.class)
    @PutMapping("/storehouseMaterial")
    @Log(name = "储物库物资",value = "修改储物库应急物资的信息")
    public ApiResult updateStorehouseMaterial(
            @ApiParam("{\n" +
                    "  \"materialId\": 2,\n" +
                    "  \"materialNum\": 57,\n" +
                    "  \"storehouseId\": 1,\n" +
                    "  \"storehouseMaterialId\": 1\n" +
                    "}")
            @RequestBody StorehouseMaterial storehouseMaterial){
        ApiResult apiResult = storehouseMaterialServer.updateStorehouseMaterial(storehouseMaterial);
        return apiResult;
    }

    /**
     * 导出储备库物资Excel
     */
    @GetMapping("/storehouseMaterial/excel")
    @ApiOperation(value = "导出储备库物资Excel",response =StorehouseMaterial.class )
    @Log(name = "应急物资配备及预案备案情况:储物库应急物资",value = "导出储备库物资Excel")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "factoryId",value = "气矿ID",defaultValue = "1"),
            @ApiImplicitParam(paramType = "query",name = "datetime",value = "时间(时间格式yyyy-MM-dd)",defaultValue = "2018-9-10")
    })
    public void getStorehouseMaterialExcelByQuery(@RequestParam(required = false) Map<String, Object> param, HttpServletResponse response) throws IOException, ClassNotFoundException {
        HSSFWorkbook wb=storehouseMaterialServer.getStorehouseMaterialExcelByQuery(param);
        //输出Excel文件
        String fileName = URLEncoder.encode("储物库应急物资信息", StandardCharsets.UTF_8.toString());
        OutputStream out = response.getOutputStream();
        response.setHeader("content-Type", "application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "; filename*=utf-8''" + fileName);
        response.setCharacterEncoding("UTF-8");
        wb.write(out);
        out.flush();
        out.close();
    }

}
