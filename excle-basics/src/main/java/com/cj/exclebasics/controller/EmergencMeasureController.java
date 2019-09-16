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
//@RequestMapping("/api/v1/emergencMeasure")
//@Api(tags = "场站、管线生产工艺录入：作业场所应急措施调查")
//@ApiIgnore
//public class EmergencMeasureController {
//    /**
//     * 获取作业场所应急措施调查表结构；
//     * 传入场站ID、表名称
//     *
//     * @param json
//     */
//    @GetMapping("/getEmergencMeasureStru")
//    @ApiOperation("获取作业场所应急措施调查表结构")
//    @Log(name = "作业场所应急措施调查操作", value = "查询作业场所应急措施调查表格结构")
//    //@ApiImplicitParam(name = "json",value = "stationId=场站id,tableName=表名称", defaultValue = "{'stationId':1,'tableName':'基础信息'}")
//    public void getEmergencMeasureStru(String json) {
//
//    }
//
//    /**
//     * 更新作业场所应急措施调查表结构
//     */
//    @PostMapping("/updateEmergencMeasureStru")
//    @Log(name = "作业场所应急措施调查操作", value = "更新作业场所应急措施调查表格结构")
//    @ApiOperation("更新作业场所应急措施调查表结构")
//    public void updateEmergencMeasureStru() {
//
//    }
//
//    /**
//     * 获取作业场所应急措施调查表（结构+内容）
//     */
//    @PostMapping("/getEmergencMeasure")
//    @Log(name = "作业场所应急措施调查操作", value = "获取作业场所应急措施调查表的内容")
//    @ApiOperation("获取作业场所应急措施调查表（结构+内容）")
//    public void getEmergencMeasure() {
//
//    }
//
//    /**
//     * 更新作业场所应急措施调查表格（仅内容）
//     */
//    @PostMapping("/updateEmergencMeasure")
//    @Log(name = "作业场所应急措施调查操作", value = "更新作业场所应急措施调查表的内容")
//    @ApiOperation("更新作业场所应急措施调查表格（内容）")
//    public void updateEmergencMeasure() {
//
//    }
//
//}
