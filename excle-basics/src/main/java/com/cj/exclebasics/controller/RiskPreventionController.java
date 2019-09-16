//package com.cj.exclebasics.controller;
//
//import com.cj.core.aop.Log;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import springfox.documentation.annotations.ApiIgnore;
//
//@RestController
//@RequestMapping("/api/v1/riskPrevention")
//@Api(tags = "场站、管线生产工艺录入：风险防范及应急措施")
//@ApiIgnore
//public class RiskPreventionController {
//    /**
//     * 获取风险防范及应急措施表结构；
//     * 传入场站ID、表名称
//     *
//     * @param json
//     */
//    @GetMapping("/getRiskPreventionStru")
//    @ApiOperation("获取风险防范及应急措施表结构")
//    @Log(name = "风险防范及应急措施操作", value = "查询风险防范及应急措施表格结构")
//    //@ApiImplicitParam(name = "json",value = "stationId=场站id,tableName=表名称", defaultValue = "{'stationId':1,'tableName':'基础信息'}")
//    public void getRiskPreventionStru(String json) {
//
//    }
//
//    /**
//     * 更新风险防范及应急措施表结构
//     */
//    @PostMapping("/updateRiskPreventionStru")
//    @Log(name = "风险防范及应急措施操作", value = "更新风险防范及应急措施表格结构")
//    @ApiOperation("更新风险防范及应急措施表结构")
//    public void updateRiskPreventionStru() {
//
//    }
//
//    /**
//     * 获取风险防范及应急措施表（结构+内容）
//     */
//    @PostMapping("/getRiskPrevention")
//    @Log(name = "风险防范及应急措施操作", value = "获取风险防范及应急措施表的内容")
//    @ApiOperation("获取风险防范及应急措施表（结构+内容）")
//    public void getRiskPrevention() {
//
//    }
//
//    /**
//     * 更新风险防范及应急措施表格（仅内容）
//     */
//    @PostMapping("/updateRiskPrevention")
//    @Log(name = "风险防范及应急措施操作", value = "更新风险防范及应急措施表的内容")
//    @ApiOperation("更新风险防范及应急措施表格（内容）")
//    public void updateRiskPrevention() {
//
//    }
//
//}