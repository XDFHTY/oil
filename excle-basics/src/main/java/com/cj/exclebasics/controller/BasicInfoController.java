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
//@RequestMapping("/api/v1/basicInfo")
//@Api(tags = "场站、管线基本信息录入：基本信息")
//@ApiIgnore
//public class BasicInfoController {
//    /**
//     * 获取基本信息表结构；
//     * 传入场站ID即可；表名称固定为：基础信息
//     *
//     * @param json
//     */
//    @GetMapping("/getBasicInfoStru")
//    @ApiOperation("获取基本信息表结构")
//    @Log(name = "基本信息操作", value = "查询基本信息表格结构")
//    //@ApiImplicitParam(name = "json",value = "stationId=场站id,tableName=表名称", defaultValue = "{'stationId':1,'tableName':'基础信息'}")
//    public void getBasicInfo(String json) {
//
//    }
//
//    /**
//     * 更新基本信息表结构
//     */
//    @PostMapping("/updateBasicInfoStru")
//    @Log(name = "基本信息操作", value = "更新基本信息表格结构")
//    @ApiOperation("更新基本信息表结构")
//    public void updateBasicInfoStru() {
//
//    }
//
//    /**
//     * 获取基本信息表（结构+内容）
//     */
//    @PostMapping("/getBasicInfo")
//    @Log(name = "基本信息操作", value = "获取基本信息表的内容")
//    @ApiOperation("获取基本信息表（结构+内容）")
//    public void getBasicInfo() {
//
//    }
//
//    /**
//     * 更新基本信息表格（仅内容）
//     */
//    @PostMapping("/updateBasicInfo")
//    @Log(name = "基本信息操作", value = "更新基本信息表的内容")
//    @ApiOperation("更新基本信息表格（内容）")
//    public void updateBasicInfo() {
//
//    }
//
//}
