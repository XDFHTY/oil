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
//@RequestMapping("/api/v1/productProcess")
//@Api(tags = "场站、管线生产工艺录入：生产工艺")
//@ApiIgnore
//public class ProductProcessController {
//    /**
//     * 获取生产工艺表结构；
//     * 传入场站ID、表名称
//     *
//     * @param json
//     */
//    @GetMapping("/getProductProcessStru")
//    @ApiOperation("获取生产工艺表结构")
//    @Log(name = "生产工艺操作", value = "查询生产工艺表格结构")
//    //@ApiImplicitParam(name = "json",value = "stationId=场站id,tableName=表名称", defaultValue = "{'stationId':1,'tableName':'基础信息'}")
//    public void getProductProcessStru(String json) {
//
//    }
//
//    /**
//     * 更新生产工艺表结构
//     */
//    @PostMapping("/updateProductProcessStru")
//    @Log(name = "生产工艺操作", value = "更新生产工艺表格结构")
//    @ApiOperation("更新生产工艺表结构")
//    public void updateProductProcessStru() {
//
//    }
//
//    /**
//     * 获取生产工艺表（结构+内容）
//     */
//    @PostMapping("/getProductProcess")
//    @Log(name = "生产工艺操作", value = "获取生产工艺表的内容")
//    @ApiOperation("获取生产工艺表（结构+内容）")
//    public void getProductProcess() {
//
//    }
//
//    /**
//     * 更新生产工艺表格（仅内容）
//     */
//    @PostMapping("/updateProductProcess")
//    @Log(name = "生产工艺操作", value = "更新生产工艺表的内容")
//    @ApiOperation("更新生产工艺表格（内容）")
//    public void updateProductProcess() {
//
//    }
//
//}
