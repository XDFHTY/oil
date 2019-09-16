package com.cj.admin.service;



import com.cj.admin.domain.IfLoginResp;
import com.cj.admin.domain.UpdateAdminByAdminPassReq;
import com.cj.common.entity.Admin;
import com.cj.core.domain.ApiResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface AdminService {

    /**
     * =====================================Admin===================================================
     */

    /**
     * 新增管理员账号
     */
    public ApiResult addAdmin(Admin admin);


    /**
     * 修改管理员密码
     */
    public int updateAdmin(Admin admin);



    /**
     * 删除管理员账号
     */
    public int delete(Long adminId);



    /**
     * 查询所有管理员账号
     */
    public List<Admin> findAllAdmin();


    /**
     * 管理员登录
     */
    public ApiResult ifLogin(Admin admin, HttpServletRequest request);

    /**
     * ADLogin
     */
    public IfLoginResp ADLogin(String userid,String token,String key);

//    /**
//     * 管理员从HSE系统登录
//     * @param admin
//     * @param request
//     * @return
//     */
//    public IfLoginResp ipLogin(Admin admin ,HttpServletRequest request);

    /**
     * 管理员注销
     */
    public int ifLogout(HttpSession session);




    /**
     * 修改密码，校验原密码
     */
    public ApiResult updateAdminByAdminPass(HttpSession session,UpdateAdminByAdminPassReq updateAdminByAdminPassReq);


}
