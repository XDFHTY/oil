
package com.cj.common.security.dto;

import com.cj.core.domain.ApiResult;
import com.cj.common.utils.http.HttpUtil;
import com.google.gson.Gson;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Iterator;

public class MyAccessDecisionManager implements AccessDecisionManager {


    /**

     * 检查用户是否够权限访问资源
     * authentication 是从spring的全局缓存SecurityContextHolder中拿到的，里面是用户的角色信息，在登录验证通过后将用户角色添加进去
     * object 是封装请求信息的对象 httpservletrequest等
     * configAttributes 访问当前请求的url所需的角色
     * @see AccessDecisionManager#decide(Authentication, Object, Collection)
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        FilterInvocation filterInvocation = (FilterInvocation) o;
        //获取response对象
        HttpServletResponse response = filterInvocation.getHttpResponse();
        Gson gson =new Gson();
        ApiResult apiResult=null;
        //权限开关
        int i = 0;

        Iterator<ConfigAttribute> ite = configAttributes.iterator();
        //判断用户所拥有的权限，是否符合对应的Url权限，如果实现了UserDetailsService，则用户权限是loadUserByUsername返回用户所对应的权限
        while (ite.hasNext()) {
            ConfigAttribute ca = ite.next();
            String needRole = ((SecurityConfig) ca).getAttribute();
            if (needRole.equals("0")){
                i = 401;
            }
            for (GrantedAuthority ga : authentication.getAuthorities()) {
//                System.out.println(":::::::::::::" + ga.getAuthority());
                //如果用户拥有的角色匹配上访问此url所需的角色，则拥有访问权限
                if (needRole.equals(ga.getAuthority())) {
//                    System.out.println(":::::::::=======拥有访问权限");
                    i = 1;
                    break;
                }
            }
            if (i==1){
                break;
            }
        }
        //如果验证权限信息验证失败
        if (i == 401 || i == 0) {
            authentication.setAuthenticated(false);
            apiResult = ApiResult.CODE_403();
            HttpUtil.doReturn(response, apiResult);

            throw new AccessDeniedException("Access failure");
        }
    }

    /**
     * @param configAttribute
     * @return
     */
    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    /**
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }


}

