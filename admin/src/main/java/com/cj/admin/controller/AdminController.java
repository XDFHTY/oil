package com.cj.admin.controller;


import com.cj.admin.domain.AddAdminResp;
import com.cj.admin.domain.IfLoginResp;
import com.cj.admin.domain.UpdateAdminByAdminPassReq;
import com.cj.admin.service.AdminService;
import com.cj.common.entity.Admin;
import com.cj.core.domain.ApiResult;
import com.cj.core.aop.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/account")
@Api(tags = "账号、角色、权限模块: 账号管理")
public class AdminController {
     Long id=null;

    @Autowired
    private AdminService adminService;



    /**
     * ============================================管理员登录、注销====================================================
     */

    /**
     * 登录
     */
    @ApiOperation(value = "管理员登录",response = IfLoginResp.class)
    @PostMapping("/ifLogin")
    @Log(name = "账号管理",value = "管理员登录")
    public ApiResult ifLogin(@ApiParam(name = "admin",value = "adminName=用户名、adminPass=密码,如：\n{\n" +
            "  \"adminName\": \"qwe\",\n" +
            "  \"adminPass\": \"123456\"\n" +
            "}")
                       @RequestBody Admin admin, HttpServletRequest request){

        ApiResult apiResult = adminService.ifLogin(admin,request);


        return apiResult;


    }



    @ApiOperation("注销")
    @GetMapping("/ifLogout")
    @Log(name = "账号管理",value = "注销")
    public ApiResult ifLogout(HttpSession session){
        ApiResult apiResult = null;
        int i = adminService.ifLogout(session);
        if(i == 0 ){
            ApiResult.FAIL().setMsg("注销失败");
            apiResult = ApiResult.FAIL();
        }else {
            apiResult = ApiResult.SUCCESS();
        }

        return apiResult;
    }


    /**
     * ==============================================管理员账号维护===========================================================
     */

    /**
     * 新增账号
     */

    @ApiOperation(value = "添加管理员账号",response = AddAdminResp.class)
    @PostMapping("/addAdmin")
    @Log(name = "账号管理",value = "添加管理员账号")
    public ApiResult addAdmin(@ApiParam(name = "admin",value = "adminName=账号、adminPass=密码,roleId=角色Id" +
            "{\n" +
            "  \"adminName\": \"q2\",\n" +
            "  \"adminPass\": \"123456\",\n" +
            "  \"roleId\": \"1\"\n" +
            "}",required = true)
                                  @RequestBody Admin admin){

        ApiResult apiResult = adminService.addAdmin(admin);
        return apiResult;
    }

    /**
     * 修改密码，不校验原密码
     */
    @ApiOperation("修改密码，不校验原密码")
    @PutMapping("/updateAdmin")
    @Log(name = "账号管理",value = "修改密码，不校验原密码")
    public ApiResult updateAdmin(@ApiParam(name = "admin",value = "id=adminId、账号=adminName、adminPass=新密码",required = true)
            @RequestBody Admin admin){
        int i = adminService.updateAdmin(admin);
        ApiResult apiResult = null;
        if(i == 1){
            apiResult = ApiResult.SUCCESS();
        }else {
            apiResult = ApiResult.FAIL();
        }

        return apiResult;
    }



    /**
     * 删除账号
     */
    @ApiOperation("删除账号")
    @DeleteMapping("/deleteAdmin")
    @Log(name = "账号管理",value = "删除账号")
    @ApiImplicitParam(name = "adminId",value = "adminId",required = true)
    public ApiResult deleteAdmins(Long adminId){

        System.out.println("===============");
        System.out.println(adminId);

        int i = adminService.delete(adminId);

        ApiResult apiResult = null;
        if(i > 0){
            apiResult = ApiResult.SUCCESS();
        }else {
            apiResult = ApiResult.FAIL();
        }

        return apiResult;

    }


    /**
     * 查询所有账号
     */
    @ApiOperation("查询所有账号")
    @GetMapping("/findAllAdmin")
    @Log(name = "账号管理",value = "查询所有账号")
    public ApiResult findAllAdmin(){
        ApiResult apiResult = null;
            apiResult = ApiResult.SUCCESS();
            apiResult.setData(adminService.findAllAdmin());

        return apiResult;
    }


    //=========================================新接口==========

    /**
     * 修改密码，校验原密码
     */
    @PutMapping("/updateAdminByAdminPass")
    @ApiOperation("修改密码，校验原密码")
    @Log(name = "账号管理",value = "修改密码，校验原密码")
    public ApiResult updateAdminByAdminPass(@ApiParam(name = "json",value = "输入新旧密码")
                                           @RequestBody UpdateAdminByAdminPassReq updateAdminByAdminPassReq,
                                       HttpSession session){

        return adminService.updateAdminByAdminPass(session,updateAdminByAdminPassReq);
    }


}
