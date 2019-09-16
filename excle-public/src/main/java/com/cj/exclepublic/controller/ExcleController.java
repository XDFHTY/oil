package com.cj.exclepublic.controller;

import com.cj.common.entity.TableRecord;
import com.cj.core.domain.ApiResult;
import com.cj.core.aop.Log;
import com.cj.exclepublic.domain.*;
import com.cj.exclepublic.service.ExcleService;
import com.google.gson.Gson;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/common/excel")
@Api(tags = "公共模块: Excle相关操作")
public class ExcleController {

    @Autowired
    private ExcleService excleService;



    //==================表结构相关操作

    /**
     * 查询Excle表结构及内容及公式
     * @param json
     * @return
     */
    @GetMapping("/findStru")
    @ApiOperation(value = "查询Excle表结构及内容及公式",response = FindExcleStruByStIdAndTabNameResp.class)
    @Log(name = "公共模块: Excle相关操作",value = "查询Excle表结构及内容及公式")
    @ApiImplicitParam(name = "json",value = "stationId=场站id,tableName=表名称,year=年份（可为空，此时只查询表结构）",
            defaultValue = "{\"stationId\":1,\"tableName\":\"基本信息\",\"year\":\"2018\"}")
    public ApiResult qryTableStru(String json,HttpServletRequest request){
        ApiResult apiResult=null;
        FindStruReq findStruReq = new Gson().fromJson(json, FindStruReq.class);//转成map
        if (findStruReq == null){
            return ApiResult.FAIL();
        }
        Long stationId = findStruReq.getStationId();
        String tableName = findStruReq.getTableName();
        String year = findStruReq.getYear();
        if (stationId != null && tableName != null) {//保证参数正确
            apiResult = excleService.findExcleStruByStIdAndTabName(stationId, tableName,year,request);
        }else {
            apiResult = ApiResult.FAIL();//参数错误则返回错误
        }
        return apiResult;
    }


    /**
     * 修改表结构信息,(从某一行后面插入一行)
     * 传入参数：stationId, tableName, 插入开始行行号,总行数，单元格集合
     */
    @PutMapping("/updateTabStru")

    @ApiOperation(value = "修改表结构信息（可以新增行）",response = String.class)
    @Log(name = "公共模块: Excle相关操作",value = "修改表结构信息（可以新增行）")
    public ApiResult updateTabStru(@ApiParam(name = "map",value = "" +
            "oldExcleTableStructures=excle表结构修改后信息集合(List<ExcleTableStructure>,包含有ID的被修改内容)，"+
            "newExcleTableStructures=excle表结构修改后信息集合(List<ExcleTableStructure>,包含没ID的新增内容)",required = true)
                             @RequestBody UpdateTabStruReq updateTabStruReq, HttpSession session){

        return excleService.updateTabStru(updateTabStruReq);
    }


    /**
     * 导出表结构及数据
     */
    @GetMapping("/exportTableStru")
    @ApiOperation("导出表结构及数据")
    @Log(name = "公共模块: Excle相关操作",value = "导出表结构及数据")
    @ApiImplicitParam(name = "json",value = "stationId=场站id,tableName=表名称,year=年份（可为空，此时只查询表结构）",
            defaultValue = "{\"stationId\":1,\"tableName\":\"基本信息\",\"year\":\"2018\"}")
    public ApiResult exportTableStru(String json, HttpServletResponse response) throws Exception {
        ApiResult apiResult=null;

        Map<String,Object> map = new Gson().fromJson(json, Map.class);//转成map

        if (map == null)
            return ApiResult.FAIL();
        Long stationId = ((Double) map.get("stationId")).longValue();
        String tableName = (String) map.get("tableName");
        String year = (String) map.get("year");
        if (stationId != null && tableName != null) {//保证参数正确
            apiResult = excleService.exportTableStru(stationId, tableName,year,response);
        }else {
            apiResult = ApiResult.FAIL();//参数错误则返回错误
        }
        return apiResult;
    }



    //======================表相关操作

    /**
     * 参数：场站ID,表格名称，年份
     * 返回：表内容列表信息
     * @return
     */
    @GetMapping("/findTableRecord")
    @ApiOperation(value = "查询表内容列表信息,查询该场站、该表、该年是否已存在数据,存在则不允许再次添加",response = TableRecord.class)
    @ApiImplicitParam(name = "json",value = "stationId=场站id,tableName=表名称(本年),year=年份",required = true,
            defaultValue = "{\"stationId\":1,\"tableName\":\"基本信息\",\"year\":\"2018\"}")
    @Log(name = "公共模块: Excle相关操作",value = "查询表内容列表信息")
    public ApiResult findTableRecord(String json){

        return excleService.findTableRecord(json);

    }

    /**
     * 参数：场站ID,表格名称，年份，表格新增内容集合
     * 返回：成功/失败
     * @param addTableCellRecordsReq
     */
    @PostMapping("/addTableCellRecords")
    @ApiOperation("批量新增表内容")
    @Log(name = "公共模块: Excle相关操作",value = "批量新增表内容")
    public ApiResult addTableCellRecords(@ApiParam(name = "addTableCellRecordsReq",value = "stationId=场站id,tableName=表名称,year=年份，" +
            "tableCellRecords=表格新增内容集合（List<TableCellRecord>，TableCellRecord对象只需要" +
            "填写Long excleTableStructureId=单元格ID、String cellMsg=单元格内容）\n" +
            "{\"stationId\":1,\"tableName\":\"基本信息\",\"year\":\"2018\",\"tableCellRecords\":[{\"excleTableStructureId\":434,\"cellMsg\":\"测试\"},{\"excleTableStructureId\":439,\"cellMsg\":\"测试2\"}]}",required = true)
                                   @RequestBody AddTableCellRecordsReq addTableCellRecordsReq, HttpServletRequest request){

        return excleService.addTableCellRecords(addTableCellRecordsReq,request);

    }

    /**
     * 参数：场站ID,表格名称，年份，表格新增内容集合
     * 返回：成功/失败
     * @param updateTableCellRecordsReq
     * @param request
     * @return
     */
    @PutMapping("/updateTableCellRecords")
    @ApiOperation("批量修改表内容")
    @Log(name = "公共模块: Excle相关操作",value = "批量修改表内容")
    public ApiResult updateTableCellRecords(@ApiParam(name = "updateTableCellRecordsReq",value = "stationId=场站id,tableName=表名称,year=年份，" +
            "tableCellRecords=表格新增内容集合（List<TableCellRecord>，TableCellRecord对象只需要" +
            "填写Long excleTableStructureId=单元格ID、String cellMsg=单元格内容）\n" +
            "{\"newExternalEnvirImgs\":[{\"imgUrl\":\"http://127.0.0.1:8085/img/1901068c-dd17-47cf-aae8-e2f57c0c768b.png\"},{\"imgUrl\":\"http://127.0.0.1:8085/img/1901068c-dd17-47cf-aae8-e2f57c0c768b.png\"}],\"stationId\":1,\"tableName\":\"外环境关系图上传\",\"year\":\"2018\"}",required = true)
                                      @RequestBody UpdateTableCellRecordsReq updateTableCellRecordsReq, HttpServletRequest request){

        return excleService.updateTableCellRecords(updateTableCellRecordsReq,request);

    }

    /**
     * 根据场站ID查询填表顺序
     * 
     * @return
     */
    @GetMapping("/findExcleSort")
    @ApiOperation(value = "根据场站ID查询填表顺序",response = FindExcleSortResp.class)
    @Log(name = "公共模块: Excle相关操作",value = "根据场站ID查询填表顺序")
    @ApiImplicitParam(name = "json",value = "场站ID",required = true,
            defaultValue = "{\"stationId\":1}")
    public ApiResult findExcleSort(String json,HttpSession session){
        Map map = new Gson().fromJson(json,Map.class);

        return excleService.findExcleSort(map,session);
        
    }
}
