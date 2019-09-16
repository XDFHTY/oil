package com.cj.exclepublic.controller;

import com.cj.core.domain.ApiResult;
import com.cj.core.aop.Log;
import com.cj.exclepublic.domain.FindOrganizationResp;
import com.cj.exclepublic.service.PowerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by XD on 2018/10/15.
 */
@RestController
@RequestMapping("/api/v1/common/query")
@Api(tags = "公共模块: 查询相关操作（权限）")
public class PowerQueryController {

    @Autowired
    private PowerService powerService;

    /**
     * 带权限查询筛选条件
     */
    @ApiOperation("带权限查询各个机构名称")
    @GetMapping("/getOrganizationList")
    @Log(name = "公共模块: 组织结构级联查询",value = "带权限查询各个机构名称")
    public ApiResult getOrganizationList(HttpServletRequest request) {
        ApiResult apiResult = this.powerService.getOrganizationList(request);
        return apiResult;
    }


    @ApiOperation(value = "根据场站ID查询其组织结构信息",response = FindOrganizationResp.class)
    @GetMapping("/findOrganization")
    @Log(name = "公共模块: 组织结构级联查询",value = "根据场站ID查询其组织结构信息")
    @ApiImplicitParam(name = "stationId",value = "场站ID",required = true,defaultValue = "1")
    public ApiResult findOrganization(Long stationId){

        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(powerService.findOrganization(stationId));

        return apiResult;

    }
}
