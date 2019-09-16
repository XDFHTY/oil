package com.cj.exclebasics.controller;

import com.cj.core.domain.ApiResult;
import com.cj.core.aop.Log;
import com.cj.exclebasics.domain.NexusImg;
import com.cj.exclebasics.service.ExternalEnvirService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/basic/externalEnvir")
@Api(tags = "场站、管线生产工艺录入: 外部环境关系图模块")
public class ExternalEnvirController {


    @Resource
    private ExternalEnvirService externalEnvirService;



    @GetMapping("/findNexusImg")
    @ApiOperation("查询外环境关系图信息")
    @Log(name = "外部环境关系图模块", value = "查询外环境关系图信息")
    @ApiImplicitParam(name = "json",value = "场站ID,年份",required = true,
            defaultValue = "{\"stationId\":1,\"tableName\":\"外环境关系图上传\",\"year\":\"2018\"}")
    public ApiResult findNexusImg(String json, HttpServletRequest request) {
        ApiResult apiResult = null;
        Map map = new Gson().fromJson(json,Map.class);

        apiResult = ApiResult.SUCCESS();
        apiResult.setData(externalEnvirService.findNexusImg(map,request));

        return apiResult;


    }


    @PostMapping("/addNexusImg")
    @ApiOperation("新增外环境关系图信息")
    @Log(name = "外部环境关系图模块", value = "新增外环境关系图信息")
    public ApiResult addNexusImg(@ApiParam(name = "map",value = "stationId=场站ID,tableName=表名，year=年份，externalEnvirImgs(List<ExternalEnvirImg>) ==参数集合\n" +
            "{\"stationId\":1,\"tableName\":\"外环境关系图上传\",\"year\":\"2018\",\"externalEnvirImgs\":[{\"imgUrl\":\"http://127.0.0.1:8085/img/1901068c-dd17-47cf-aae8-e2f57c0c768b.png\"},{\"imgUrl\":\"http://127.0.0.1:8085/img/1901068c-dd17-47cf-aae8-e2f57c0c768b.png\"}]}",
            required = true)
                                @RequestBody NexusImg nexusImg, HttpServletRequest request){
        ApiResult apiResult = null;


        int i = externalEnvirService.addNexusImg(nexusImg,request);

        if (i == -1){
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("每个场站 每年 只能新增一次 表");

        }else if (i > 0){
            apiResult = ApiResult.SUCCESS();
        }else {
            apiResult = ApiResult.FAIL();
        }

        return apiResult;
    }

    @PutMapping("/updateNexusImg")
    @ApiOperation("修改外环境关系图信息")
    @Log(name = "外部环境关系图模块", value = "修改外环境关系图信息")
    public ApiResult updateNexusImg(@ApiParam(name = "map",value = "stationId=场站ID,tableName=表名，year=年份，" +
            "oldExternalEnvirImgs==查询出来的带ID的参数集合" +
            "newExternalEnvirImgs==新上传的无ID的参数集合\n" +
            "",required = true)
                                  @RequestBody NexusImg nexusImg, HttpServletRequest request){

        ApiResult apiResult = null;


        int i = externalEnvirService.updateNexusImg(nexusImg,request);

        if (i > 0){
            apiResult = ApiResult.SUCCESS();
        }else {
            apiResult = ApiResult.FAIL();
        }

        return apiResult;
    }





}
