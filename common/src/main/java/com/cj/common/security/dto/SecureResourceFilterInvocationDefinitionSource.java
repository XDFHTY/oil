package com.cj.common.security.dto;

import com.cj.common.domain.AuthModulars;
import com.cj.common.domain.AuthRoleModulars;
import com.cj.core.domain.MemoryData;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecureResourceFilterInvocationDefinitionSource implements FilterInvocationSecurityMetadataSource, InitializingBean {
    List<AuthRoleModulars> authRoleModulars=null;
    PathMatcher matcher=null;

    /**
     * 初始化用户权限，为了简便操作没有从数据库获取
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //初始化匹配工具

    }

    /**当访问的Url和资源路径url匹配时，返回该Url所需要的权限
     * @param o
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //储存访问url所需的角色
        ArrayList<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
        //标志
        boolean flag = false;
        matcher =new AntPathMatcher();
        //安全配置信息集合
        //memorydata中获取当前用户的对应权限信息和角色信息
        authRoleModulars = (List<AuthRoleModulars>) MemoryData.getRoleModularMap().get("modulars");
        FilterInvocation filterInvocation = (FilterInvocation) o;
        //拦截请求url
        String requestURI = filterInvocation.getRequestUrl();
        if (requestURI.indexOf("?") != -1){
            requestURI = requestURI.substring(0,requestURI.indexOf("?"));
        }

        //拦截请求方法
        String requestMethod = filterInvocation.getHttpRequest().getMethod();
        HttpServletResponse response = filterInvocation.getHttpResponse();
        //循环资源路径，当访问的Url和资源路径url匹配时，返回该Url所需要的权限
        Iterator<AuthRoleModulars> itr1 = authRoleModulars.iterator();
        System.out.println();
        while (itr1.hasNext()){
            AuthRoleModulars authRoleModulars1 = itr1.next();
            AuthModulars authModulars1 = new AuthModulars();
            //迭代获得2级目录
            Iterator<AuthModulars> itm2 = authRoleModulars1.getAuthModulars().getChildren().iterator();
            while (itm2.hasNext()){
                AuthModulars authModulars2 = itm2.next();
                //迭代获取3级目录
                Iterator<AuthModulars> itm3 = authModulars2.getChildren().iterator();
                while (itm3.hasNext()){
                    AuthModulars authModulars3 = itm3.next();
                    //迭代获得权限列表
                    Iterator<AuthModulars> itm0 =  authModulars3.getChildren().iterator();
                    while (itm0.hasNext()){
                        //数据库中接口信息
                        AuthModulars authModulars00 = itm0.next();
                        //接口url
                        String authUrl=authModulars00.getPageUrl();
                        //对应方法
                        String authMethod=authModulars00.getPageMethod();

                        //验证请求url接口是否存在、和方法头是否匹配
                        if (restFulUrlMatch(requestMethod,authMethod,requestURI,authUrl)) {
//                            System.out.println("==========访问此url所需的角色:  "+authRoleModulars1.getRoleName() );
                            //将访问此url所需的角色添加到attsno
                            configAttributes.add(new SecurityConfig(authRoleModulars1.getRoleName()));
                            //匹配成功
                            if (!flag) {
                                flag = true;
                                break;
                            }


                        }

                    }
                }


            }

        }

        if (configAttributes.size() == 0) { //请求的url接口不存在数据库
            configAttributes.add(new SecurityConfig("0"));
        }
        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**返回此配置信息源是否可用
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }



    /**
     * restFul风格url匹配
     * @param reqMethod
     * @param authMethod
     * @param reqUrl
     * @param authUrl
     * @return
     */
    private static boolean restFulUrlMatch(String reqMethod,String authMethod,String reqUrl,String authUrl){
        Boolean result = false;
        //如果角色下该接口无请求方式限制
        if ("ALL".equals(authMethod) ) result=true;
        //如果请求方式匹配
        if ( reqMethod.equals(authMethod))result=true;

        //当请求方式匹配成功后，匹配url
        if (result){
            String regx0 = "\\{[\\s\\S]*\\}";
            String regx1 = "[\\\\s\\\\S]*";
            String regx="";
            //不存在{}
            if (authUrl.indexOf("{") == -1 && authUrl.indexOf("}") == -1){
                regx = "^"+authUrl+"$";
            }else {
                regx = authUrl.replaceAll(regx0,regx1);

            }

            Pattern pattern = Pattern.compile(regx);
            Matcher matcher = pattern.matcher(reqUrl);
            // 字符串是否与正则表达式相匹配
            boolean rs = matcher.matches();

            return rs;
        }

        return false;
    }

}
