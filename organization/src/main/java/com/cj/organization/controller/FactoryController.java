package com.cj.organization.controller;

import com.cj.core.domain.ApiResult;
import com.cj.core.domain.Pager;
import com.cj.core.aop.Log;
import com.cj.organization.entity.RespOrganization;
import com.cj.organization.service.FactoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("api/v1/organization/info")
@Api(tags = "组织结构信息管理 ==> 组织结构信息管理")
public class FactoryController {

    @Autowired
    private FactoryService factoryService;


    /**
     * 根据厂、矿Id 查询 气矿基本信息和管理人信息
     * factoryId=气矿id（非必传，为null则查询全部）
     * @param p
     * @param request
     * @return
     */
    @ApiOperation(value = "分页条件查询 气矿基本信息和管理人信息", response = RespOrganization.class)
    //@PostMapping("/findFactoryById")
    @Log(name = "厂、矿管理 ==> 条件查询 气矿基本信息和管理人信息")
    public ApiResult findFactoryById(
            @ApiParam(name = "p", value = "查询条件=parameters=" +"【factoryId=气矿id（非必传，为null则查询全部）,"
                    ,required = true)
            @RequestBody Pager p, HttpServletRequest request){

        ApiResult apiResult = factoryService.findFactoryById(p,request);
        return apiResult;
    }

}
