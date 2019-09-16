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
//@RequestMapping("/api/v1/conseqSimu")
//@Api(tags = "场站、管线生产工艺录入：后果模拟")
//@ApiIgnore
//public class ConseqSimuController {
//    /**
//     * 获取后果模拟表结构；
//     * 传入场站ID、表名称
//     *
//     * @param json
//     */
//    @GetMapping("/getConseqSimuStru")
//    @ApiOperation("获取后果模拟表结构")
//    @Log(name = "后果模拟操作", value = "查询后果模拟表格结构")
//    //@ApiImplicitParam(name = "json",value = "stationId=场站id,tableName=表名称", defaultValue = "{'stationId':1,'tableName':'基础信息'}")
//    public void getConseqSimuStru(String json) {
//
//    }
//
//    /**
//     * 更新后果模拟表结构
//     */
//    @PostMapping("/updateConseqSimuStru")
//    @Log(name = "后果模拟操作", value = "更新后果模拟表格结构")
//    @ApiOperation("更新后果模拟表结构")
//    public void updateConseqSimuStru() {
//
//    }
//
//    /**
//     * 获取后果模拟表（结构+内容）
//     */
//    @PostMapping("/getConseqSimu")
//    @Log(name = "后果模拟操作", value = "获取后果模拟表的内容")
//    @ApiOperation("获取后果模拟表（结构+内容）")
//    public void getConseqSimu() {
//
//    }
//
//    /**
//     * 更新后果模拟表格（仅内容）
//     */
//    @PostMapping("/updateConseqSimu")
//    @Log(name = "后果模拟操作", value = "更新后果模拟表的内容")
//    @ApiOperation("更新后果模拟表格（内容）")
//    public void updateConseqSimu() {
//
//    }
//
//}
