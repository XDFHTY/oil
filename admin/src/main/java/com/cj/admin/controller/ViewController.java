package com.cj.admin.controller;

import com.cj.admin.domain.IfLoginResp;
import com.cj.admin.service.AdminService;
import com.cj.common.entity.Admin;
import com.cj.core.domain.ApiResult;
import com.cj.core.aop.Log;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/api/v1/admin/account")
@Api(tags = "账号、角色、权限模块: 账号管理")
public class ViewController {


    @Autowired
    private AdminService adminService;

//    @ApiOperation(value = "管理员通过HES进入系统，只校验ip和用户名")
//    @GetMapping("/ipLogin")
//    @Log(name = "账号管理",value = "管理员从HSE登录")
//    public String ipLogin(String userName, HttpServletRequest request) throws UnsupportedEncodingException {
//
//        Admin admin = new Admin();
//        admin.setAdminName(userName);
//        IfLoginResp ifLoginResp  = adminService.ipLogin(admin,request);
//
//        ApiResult apiResult = ApiResult.SUCCESS();
//        apiResult.setData(ifLoginResp);
//        String data = new Gson().toJson(apiResult);
//        data = URLEncoder.encode(data,"utf-8");
//
//        String url="http://" + request.getServerName() //服务器地址
//                + ":"
//                + request.getServerPort();          //端口号
//        return "redirect:" + url +"/static/index.html?" + data;
//    }

    @ApiOperation("单点登录")
    @GetMapping("/ADLogin")
    public String ADLogin(
            @RequestParam("userid ") String userid,
            @RequestParam("token") String token,
            @RequestParam("key ") String key,
            HttpServletRequest request
    ) throws UnsupportedEncodingException {

        IfLoginResp ifLoginResp = adminService.ADLogin(userid,token,key);

        if (ifLoginResp==null){
            //重定向到单点登录页面
            return "redirect:http://portal01.xnyqt.petrochina:10039/wps/portal/";
        }
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(ifLoginResp);
        String data = new Gson().toJson(apiResult);
        data = URLEncoder.encode(data,"utf-8");
        String url="http://" + request.getServerName() //服务器地址
                + ":"
                + request.getServerPort();          //端口号
        return "redirect:" + url +"/static/index.html?" + data;

    }

}
