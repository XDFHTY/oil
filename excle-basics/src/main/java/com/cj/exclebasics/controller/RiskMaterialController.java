//package com.cj.exclebasics.controller;
//
//import com.cj.core.domain.Pager;
//import com.cj.core.aop.Log;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.web.bind.annotation.*;
//import springfox.documentation.annotations.ApiIgnore;
//
//@RestController
//@RequestMapping("/api/v1/riskMaterial")
//@Api(tags = "场站、管线基本信息录入：风险物质和数量")
//@ApiIgnore
//public class RiskMaterialController {
//
//    // Q值
//    /**
//     * 获取风险物质和数量 的 表结构
//     * 传入场站ID、表名称
//     * @param json
//     */
//    @GetMapping("/getRiskMaterialStru")
//    @ApiOperation("获取风险物质和数量表结构")
//    @Log(name = "风险物质和数量操作", value = "查询风险物质和数量表格结构")
//    //@ApiImplicitParam(name = "json",value = "stationId=场站id,tableName=表名称", defaultValue = "{'stationId':1,'tableName':'基础信息'}")
//    public void getRiskMaterialStru(@RequestBody  Pager pager) {
//
//    }
//
//    /**
//     * 更新风险物质和数量表结构
//     */
//    @PostMapping("/updateRiskMaterialStru")
//    @Log(name = "风险物质和数量操作", value = "更新风险物质和数量表格结构")
//    @ApiOperation("更新风险物质和数量表结构")
//    public void updateRiskMaterialStru(Pager pager) {
//
//    }
//
//    /**
//     * 获取风险物质和数量表（结构+内容）
//     */
//    @PostMapping("/getRiskMaterial")
//    @Log(name = "风险物质和数量操作", value = "获取风险物质和数量表的内容")
//    @ApiOperation("获取风险物质和数量表（结构+内容）")
//    public void getRiskMaterial() {
//
//    }
//
//    /**
//     * 更新风险物质和数量表格（仅内容）
//     */
//    @PostMapping("/updateRiskMaterial")
//    @Log(name = "风险物质和数量操作", value = "更新风险物质和数量表的内容")
//    @ApiOperation("更新风险物质和数量表格（内容）")
//    public void updateRiskMaterial() {
//
//    }
//
//}
