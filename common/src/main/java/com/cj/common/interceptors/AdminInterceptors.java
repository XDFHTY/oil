package com.cj.common.interceptors;

import com.cj.common.utils.jwt.JwtUtil;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.MemoryData;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by XD on 2018/1/8.
 * 后端拦截器
 */
public class AdminInterceptors implements HandlerInterceptor {

    //不校验登陆的
    String[] urls = new String[] {
            "/api/v1/admin/ifLogin"//登录
//            ,
//            "/api/v1/admin/ifLogout"//注销

    };

    //不校验权限的【如果要校验权限，必须校验登录】
    String[] urlss = new String[]{
            "/api/v1/admin/ifLogin"//登录
            ,
            "/api/v1/admin/ifLogout"//注销

    };

    Gson gson = new Gson();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
//        System.out.println(">>>AdminInterceptors>>>>>>>在请求处理之前进行调用（Controller方法调用之前）");

//        return true;// 只有返回true才会继续向下执行，返回false取消当前请求

        String Path = request.getContextPath();  //上下文路径
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+Path+"/"; //全路径


        String requestURI = request.getRequestURI();  //请求全路径
        String path = request.getServletPath();  //请求路径
        System.out.println("===========================================================================================");
        System.out.println("Path===>"+Path);
        System.out.println("basePath===>"+basePath);
        System.out.println("requestURI===>"+requestURI);
        System.out.println("path===>"+path);
        System.out.println("===========================================================================================");
        boolean boo1 = false;  //默认校验登录
        boolean boo2 = false;  //默认校验权限

        System.out.println("============================登录、权限校验拦截器====================================");
        System.out.println(requestURI);
        System.out.println("当前请求路径================="+path);

        System.out.println("============================登录、权限校验拦截器====================================");

        ApiResult apiResult = null;

        try {

            //不检验登陆的url
            for (String url : urls) {
                if (url.equals(path)) {
                    boo1 = false;
                    break;

                }
            }

            //不检验权限的url
            for (String url : urlss) {
                if (url.equals(path)) {
                    boo2 = false;
                    break;

                }
            }

            //TODO:当前不校验权限
            boo2 = false;



            boolean boo4 = true;  //是否放行总开关
            boolean boo5 = true;  //单设备登录 token 校验
            Claims claims = null;
            //校验登录
            if (boo1) {

                //校验token
                String token = request.getHeader("token");
                if(token == null){
                    System.out.println("=====================admin token不存在===================================");

                    //token不存在
                    apiResult = ApiResult.CODE_401();
                    String str = gson.toJson(apiResult);
                    doReturn(response,str);

                    return false;

                }else {
                    claims = JwtUtil.getClaims(token,request);
                    //获取 token 中的 adminId
                    Long adminId  = new Long((Integer) claims.get("adminId"));
                    String adminName  = (String) claims.get("adminName");
                    //将 adminId 保存到 session
                    request.getSession().setAttribute("adminId",adminId);
                    request.getSession().setAttribute("adminName",adminName);

                    //adminId存在
                    String tokenKey = "a"+adminName;
                    //获取tokenMap中的token
                    String oldToken = MemoryData.getTokenMap().get(tokenKey);
                    //令牌失效
                    if(oldToken ==null){
                        System.out.println("=====================admin 令牌失效===================================");
                        boo4 = false;
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("令牌失效");
                        String str = gson.toJson(apiResult);
                        doReturn(response,str);
                        return false;
                    }


                    if(!token.equals(oldToken)){
                        System.out.println("=====================admin 用户已在其他设备登录===================================");
                        //token,用户已在其他设备登录
                        boo4 = false;
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("用户已在其他设备登录");
                        String str = gson.toJson(apiResult);
                        doReturn(response,str);
                        System.out.println();
                        return false;
                    }

                    if(adminId == null || adminId < 1 ){  //未登录
                        System.out.println("====================admin 未登录============================");
                        //没有登陆，返回状态码

                        boo4 = false;
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("未登录");
                        String str = gson.toJson(apiResult);
                        doReturn(response,str);

                        System.out.println();
                        return false;
                    }else if(adminId > 1){

                        System.out.println("=====================更新 admin token有效期===================================");
                        //更新令牌有效期
                        MemoryData.getTokenMap().put(tokenKey,token);


                    }



                }



            }else {  //不校验登陆
                System.out.println("====================无需校验登陆============================");


            }

            if(boo2){
                //校验权限
                //检查当前用户的角色
                Long roleId = new Long ((Integer)claims.get("roleId"));

//                List<String> powerList = (List<String>) session.getAttribute("powerList");
//                System.out.println("用户所拥有的请求路径===>>"+powerList);

                boolean boo3 = false;  //权限校验,默认不放行
//                if(powerList != null  && powerList.size() > 0){
//
//                    for (String s : powerList){
//                        if (s.equals(path)){
//                            //如果权限url集合里面有url和当前请求path匹配上了
//                            boo3 = true;
//                            break;
//                        }
//                    }
//
//                }

                //如果roleId = 1,代表账号是管理员
                if(roleId != null && roleId == 1){
                    boo3 = true;
                }

                if (boo3){
                    System.out.println("===================通过权限校验===============================");

                }else {
                    System.out.println("===================无权限===============================");
                    boo4 = false;

                    apiResult = ApiResult.CODE_401();
                    String str = gson.toJson(apiResult);
                    doReturn(response,str);

                    System.out.println();

                    return false;
                }



            }else {
                System.out.println("====================无需校验权限============================");

            }


            if(boo4){
                return true;
            }


        } finally {
            request = null;
        }

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
//        System.out.println(">>>AdminInterceptors>>>>>>>请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
//        System.out.println(">>>AdminInterceptors>>>>>>>在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");
    }


    public void doReturn(HttpServletResponse response,String str) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null ;
        out = response.getWriter();
        out.append(str);
        out.flush();
        out.close();
    }

}
