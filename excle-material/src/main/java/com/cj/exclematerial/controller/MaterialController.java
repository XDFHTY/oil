package com.cj.exclematerial.controller;

import com.cj.common.entity.Material;
import com.cj.core.aop.Log;
import com.cj.common.pojo.Query;
import com.cj.core.domain.ApiResult;
import com.cj.exclematerial.pojo.MaterialModel;
import com.cj.exclematerial.serveice.MaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/emergency")
@Api(tags = "应急物资")
public class MaterialController {

    @Autowired
    private MaterialService materialService;
    /**
     * 通过query条件查询物资
     * @param query
     */
    @ApiOperation("查询物资信息")
    @GetMapping("/material/query")
    @Log(name = "场站物资信息管理",value = "通过query条件查询物资")
    public void getMaterialByQuery(Query query){

    }

    @ApiOperation("根据ID查询物资信息")
    @GetMapping("/material")
    @Log(name = "场站物资信息管理",value = "根据ID查询物资信息")
    public void getMaterialById(Query query){

    }

    /**
     * 添加气矿物资信息
     * 如果改物资名字存在，则添加物资
     * 刘磊
     * @param material
     */
    @ApiOperation(value = "添加气矿物资信息",response = MaterialModel.class)
    @PostMapping("/material/addFactoryMaterial")
    @Log(name = "气矿物资信息管理",value = "添加气矿物资信息")
    public ApiResult addFactoryMaterial(@RequestBody MaterialModel material){
        ApiResult apiResult= ApiResult.SUCCESS();
        apiResult.setData(materialService.addMaterial(material));
        return apiResult;
    }

}
