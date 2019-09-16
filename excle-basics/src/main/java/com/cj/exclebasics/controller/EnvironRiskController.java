//package com.cj.exclebasics.controller;
//
//import com.cj.core.aop.Log;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.web.bind.annotation.*;
//import springfox.documentation.annotations.ApiIgnore;
//
//@RestController
//@RequestMapping("/api/v1/environRisk")
//@Api(tags = "场站、管线基本信息录入：环境风险受体")
//@ApiIgnore
//public class EnvironRiskController {
//
//    // E值
//    /**
//     * 获取环境风险受体 的 表结构
//     * 传入场站ID、表名称
//     * @param json
//     */
//    @GetMapping("/getEnvironRiskStru")
//    @ApiOperation("获取环境风险受体表结构")
//    @Log(name = "环境风险受体操作", value = "查询环境风险受体表格结构")
//    public void getEnvironRiskStru() {
//
//    }
//
//    /**
//     * 更新环境风险受体表结构
//     */
//    @PostMapping("/updateEnvironRiskStru")
//    @Log(name = "环境风险受体操作", value = "更新环境风险受体表格结构")
//    @ApiOperation("更新环境风险受体表结构")
//    public void updateEnvironRiskStru() {
//
//    }
//
//    /**
//     * 获取环境风险受体表（结构+内容）
//     */
//    @PostMapping("/getEnvironRisk")
//    @Log(name = "环境风险受体操作", value = "获取环境风险受体表的内容")
//    @ApiOperation("获取环境风险受体表（结构+内容）")
//    public void getEnvironRisk() {
//
//    }
//
//    /**
//     * 更新环境风险受体表格（仅内容）
//     */
//    @PostMapping("/updateEnvironRisk")
//    @Log(name = "环境风险受体操作", value = "更新环境风险受体表的内容")
//    @ApiOperation("更新环境风险受体表格（内容）")
//    public void updateEnvironRisk() {
//
//    }
//
//}
