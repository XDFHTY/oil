package com.cj.exclegrade.controller;

import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.exclegrade.pojo.EnvironmentGradeView;
import com.cj.exclegrade.server.EnvironmentGradeService;
import com.cj.exclegrade.vo.EnvironmentGradeVo;
import com.cj.exclegrade.vo.VoEnvironment;
import com.cj.exclegrade.vo.VoEnvironmentStation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Api(tags = "环境结果评估模块：环境评估结果管理")
@RestController
@RequestMapping("/api/v1/environmentGrade")
public class EnvironmentGradeController {

    @Autowired
    EnvironmentGradeService environmentGradeService;
    /**
     * 添加环境评估结果
     * @param environmentGradeVo 环境评估结果信息
     */
    @ApiOperation(value = "添加环境评估结果")
    @PostMapping("/res")
    @Log(name = "环境结果评估模块：环境评估结果管理",value = "添加环境评估结果")
    public ApiResult addEnvironmentGrade(
            @ApiParam()
            @RequestBody EnvironmentGradeVo  environmentGradeVo,HttpServletRequest request){
        ApiResult apiResult=environmentGradeService.addEnvironmentGrade(environmentGradeVo,request);
        return apiResult;
    }

    /**
     * 修改环境评估结果
     * @param environmentGrade 环境评估结果信息
     */
    @ApiOperation(value = "修改环境评估结果")
    @PutMapping("/res")
    @Log(name = "环境结果评估模块：环境评估结果管理",value = "修改环境评估结果")
    public ApiResult updateEnvironmentGrade(
            @ApiParam()
            @RequestBody EnvironmentGradeVo  environmentGrade, HttpServletRequest request){
        ApiResult apiResult=environmentGradeService.updateEnvironmentGrade(environmentGrade,request);
        return apiResult;

    }

    /**
     * 根据条件查询环境评估结果
     * @param query 查询条件
     */

    @ApiOperation(value = "根据条件查询场站环境评估结果(场站)",response = EnvironmentGradeView.class )
    @GetMapping("/res/station")
    @Log(name = "环境结果评估模块：环境评估结果管理",value = "根据条件查询环境评估结果(场站)")
    public ApiResult getStationEnvironmentGradeByQuery(VoEnvironmentStation query,HttpSession httpSession){
        ApiResult apiResult=environmentGradeService.findEnvironmentGrade(query,httpSession);
        return apiResult;
    }
    /**
     * 根据条件查询环境评估结果
     * @param query 查询条件
     */
    @ApiOperation(value = "根据条件查询场站环境评估结果(厂、矿)",response = EnvironmentGradeView.class)
    @GetMapping("/res/factory")
    @Log(name = "环境结果评估模块：环境评估结果管理",value = "根据条件查询环境评估结果（厂、矿）")
    public ApiResult getFactoryEnvironmentGradeByQuery(VoEnvironment query){
        query.setCurrentPage(query.getCurrentPage()-1);
        query.setCurrentIndex(query.getCurrentPage()*query.getPageSize());
        ApiResult apiResult=environmentGradeService.findFactoryEnvironmentGrade(query);
        return apiResult;
    }
    /**
     * 获取环境评估计算结果
     * @param voEnvironmentStation 查询条件
     */
    @ApiOperation(value = "获取环境评估计算结果",response = EnvironmentGradeView.class)
    @PutMapping("/res/station")
    @Log(name = "环境结果评估模块：环境评估结果管理",value = "获取环境评估计算结果")
    public ApiResult getStationResult(@RequestBody VoEnvironmentStation voEnvironmentStation,HttpSession session){
        ApiResult apiResult=environmentGradeService.getStationResult(voEnvironmentStation,session);
        return apiResult;
    }

}
