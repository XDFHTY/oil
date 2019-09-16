package com.cj.exclematerial.controller;

import com.cj.common.entity.StationMaterial;
import com.cj.common.pojo.StationMaterialVo;
import com.cj.core.domain.ApiResult;
import com.cj.core.aop.Log;

import com.cj.exclematerial.annotation.FileName;
import com.cj.exclematerial.pojo.StationMaterialEditVo;
import com.cj.exclematerial.pojo.StationMaterialQo;
import com.cj.exclematerial.serveice.StationMaterialService;

import io.swagger.annotations.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/emergency")
@Api(tags = "应急物资配备及预案备案情况：场站的物资信息管理")
public class StationMaterialController {
    @Autowired
    StationMaterialService stationMaterialService;

    /**
     * 获取场站物资信息
     */
    @GetMapping("/stationMaterial")
    @ApiOperation(value = "按条件获取场站物资信息",response =StationMaterialVo.class)
    @Log(name = "场站物资信息管理",value = "按条件获取场站物资信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "stationId",value = "场矿ID",defaultValue = "1"),
            @ApiImplicitParam(paramType = "query",name = "datetime",value = "时间(时间格式yyyy)",defaultValue = "2018-9-10")
    })
    public ApiResult getStationMaterialByQuery(@RequestParam(required = false) Map<String, Object> param){
        ApiResult apiResult=stationMaterialService.findStationMaterialByQuery(param);
        return apiResult;
    }


    /**
     * 获取Excel
     */
    @GetMapping("/stationMaterial/excel")
    @ApiOperation(value = "按条件获取场站应急物资调查表信息",response = StationMaterial.class)
    @Log(name = "场站物资信息管理",value = "按条件获取场站应急物资调查表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "stationId",value = "场站ID",defaultValue = "1"),
            @ApiImplicitParam(paramType = "query",name = "datetime",value = "时间(时间格式yyyy-MM-dd)",defaultValue = "2018-9-10")
    })
    public void getStationMaterialExcelByQuery(long stationId,String datetime, HttpServletResponse response, HttpServletRequest request) throws IOException, ClassNotFoundException {
        HSSFWorkbook wb=stationMaterialService.getStationMaterialExcelByQuery(stationId,datetime,request);
        //输出Excel文件
        String fileName = URLEncoder.encode("应急物资调查表信息", StandardCharsets.UTF_8.toString());
        OutputStream out = response.getOutputStream();
        response.setHeader("content-Type", "application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "; filename*=utf-8''" + fileName);
        response.setCharacterEncoding("UTF-8");
        wb.write(out);
        out.flush();
        out.close();
    }
/*
    */
/**
 * 添加场站物资信息
 * *如果物资信息没有，需要先添加物资
 * @param stationMaterial
 *//*

    @ApiOperation("添加场站物资信息")
    @Log(name = "场站物资信息管理",value = "添加场站物资信息")
    @PostMapping("/stationMaterial")
    public ApiResult addStationMaterial(
            @ApiParam("{\n" +
                    "\"materialId\": 2,\n" +
                    "\"materialNum\": 27,\n" +
                    "\"stationId\": 5\n" +
                    "}")
            @RequestBody StationMaterial stationMaterial){
        ApiResult apiResult=stationMaterialService.addStationMaterial(stationMaterial);
        return apiResult;
    }
*/

    /**
     * 添加场站物资信息
     * *如果物资信息没有，需要先添加物资
     * @param stationMaterials
     */
    @ApiOperation(value = "批量添加场站物资信息",response = StationMaterial.class)
    @Log(name = "场站物资信息管理",value = "批量添加场站物资信息")
    @PostMapping("/stationMaterial/list")
    public ApiResult addStationMaterials(
            @ApiParam("[\n" +
                    "{\n" +
                    "\"materialId\": 2,\n" +
                    "\"materialNum\": 50,\n" +
                    "\"stationId\": 1\n" +
                    "},\n" +
                    "{\n" +
                    "\"materialId\": 2,\n" +
                    "\"materialNum\": 100,\n" +
                    "\"stationId\": 2\n" +
                    "}\n" +
                    "]")
            @RequestBody List<StationMaterial> stationMaterials){
        ApiResult apiResult=stationMaterialService.addStationMaterials(stationMaterials);
        return apiResult;
    }

    /**
     * 修改场站物资信息
     * @param stationMaterialEditVo
     */
    @PutMapping("/stationMaterial")
    @ApiOperation(value = "修改场站物资信息",response =ApiResult.class )
    @Log(name = "场站物资信息管理",value = "修改场站物资信息")
    public ApiResult updateOrSaveStationMaterial(@RequestBody StationMaterialEditVo stationMaterialEditVo){
        ApiResult apiResult=stationMaterialService.updateOrSaveStationMaterial(stationMaterialEditVo);
        return apiResult;
    }


    @DeleteMapping("/stationMaterial")
    @ApiOperation("删除场站本年物资")
    @Log(name = "场站物资信息管理",value = "删除物资")
    @ApiImplicitParam(name = "stationMaterialId",value = "场站物资表主键",required = true)
    public ApiResult deleteStationMaterial(@RequestParam long stationMaterialId){
        ApiResult apiResult=stationMaterialService.deleteStationMaterial(stationMaterialId);
        return apiResult;
    }

    @GetMapping("/dynamic")
    @ApiOperation(value = "获取动态表",response =ApiResult.class )
    @Log(name = "场站物资信息管理",value = "获取动态表")
    public ApiResult findDynamicStationMaterial(StationMaterialQo stationMaterialQo,HttpServletRequest request){
        ApiResult apiResult=stationMaterialService.findDynamicStationMaterial(stationMaterialQo,request);
        return  apiResult;
    }

    @PostMapping("/addMaterial")
    @ApiOperation(value = "添加场站物资",response =ApiResult.class )
    public ApiResult addMaterial(@RequestBody StationMaterialEditVo stationMaterialEditVo){
        ApiResult apiResult=stationMaterialService.addMaterial(stationMaterialEditVo);
        return apiResult;
    }
}
