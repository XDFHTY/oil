package com.cj.exclepublic.read;

import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/common/excel")
@Api(tags = "Excle结构读取")
public class ReadExcleController {


    @Resource
    private ReadService readExcle;

    /**
     * 读取Excle表结构并写入数据库
     * @return
     */
    @PostMapping("/readExcle")
    @ApiOperation("读取Excle表结构并写入数据库")
    @Log(name = "公共模块: Excle相关操作",value = "读取Excle表结构并写入数据库")
    @ApiImplicitParam(name = "b",value = "是否重新写入表结构，false则只更新表结构内容",required = true,defaultValue = "false")
    public ApiResult readExcle(@ApiParam(name = "multipartFile",value = "文件",required = true)
                                       MultipartFile multipartFile,
                               Boolean b) throws Exception {



        return  readExcle.readExcle(multipartFile,b);
    }
}
