package com.cj.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "server的Controller")
@ApiIgnore
@RestController
@RequestMapping("/test")
public class ServerController {


    @ApiOperation("测试swagger扫描")
    @GetMapping("/")
    public void testServer() throws Exception {

        throw new Exception();
    }
}
