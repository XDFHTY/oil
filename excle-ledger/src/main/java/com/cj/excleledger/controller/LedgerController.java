package com.cj.excleledger.controller;

import com.cj.core.domain.ApiResult;
import com.cj.core.aop.Log;
import com.cj.excleledger.service.LedgerService;
import com.cj.exclepublic.domain.AddTableCellRecordsReq;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 年度台账模块
 * Created by XD on 2018/10/17.
 */
@RestController
@RequestMapping("api/v1/ledger/manage")
@Api(tags = "环境风险台账模块 ==> 台账管理")
public class LedgerController {
    @Autowired
    private LedgerService ledgerService;


    /**
     * 生成台账
     * 根据场站、年份和等级  拉取表结构及表数据 部分数据需调用
     * @param map
     * @param request
     * @return
     */
    @ApiOperation("生成台账")
    @PostMapping("/generateLedger")
    @Log(name = "台账管理 ==> 生成台账")
    public ApiResult generateLedger(
            @ApiParam(name = "map", value = "参数=" +"stationIdList=场站id集合," +
                    "grade=风险等级（1-较大 2-重大 0-一般）   year=年份（必传）\n " +
                    "{\"stationIdList\":[1],\"grade\":2,\"year\":\"2018\"}"
                    ,required = true)
            @RequestBody Map map, HttpServletRequest request){

        ApiResult apiResult = this.ledgerService.generateLedger(map,false,request);
        return apiResult;
    }


    @ApiOperation(value = "导出台账", notes = "成功/失败")
    @PostMapping("/ledgerExport")
    @Log(name = "台账管理 ==> 导出台账")
    public ApiResult ledgerExport(
            @ApiParam(name = "map", value = "参数=" +"stationIdList=场站id集合," +
                    "grade=风险等级（1-较大 2-重大 0-一般） tableName=表名  year=年份（必传）\n " +
                    "{\"stationIdList\":[1],\"grade\":2,\"year\":\"2018\"，\"tableName\":\"环境风险台账(重大)\"}"
                    ,required = true)
            @RequestBody Map map, HttpServletResponse response, HttpServletRequest request){
        //用什么软件打开
        response.setHeader("content-Type", "application/vnd.ms-excel");
        //下载文件的默认名字
        try {
            response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("AccountFormat.xlsx", "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ApiResult apiResult = this.ledgerService.ledgerExport(map,response,request);
        return apiResult;
    }


    /**
     * 新增或修改 台账数据
     * @param
     */
    @PostMapping("/updateLedgerData")
    @ApiOperation("新增或修改台账数据")
    @Log(name = "台账模块: Excle相关操作",value = "新增或修改台账数据")
    public ApiResult updateLedgerData(@ApiParam(name = "addTableCellRecordsReqs",value = "stationId=场站id,tableName=表名称,year=年份，" +
            "tableCellRecords=表格新增内容集合（List<TableCellRecord>，TableCellRecord对象只需要" +
            "填写Long excleTableStructureId=单元格ID、String cellMsg=单元格内容）",required = true)
                                   @RequestBody List<AddTableCellRecordsReq> addTableCellRecordsReqs, HttpServletRequest request){

        return ledgerService.updateLedgerData(addTableCellRecordsReqs,request);

    }

}
