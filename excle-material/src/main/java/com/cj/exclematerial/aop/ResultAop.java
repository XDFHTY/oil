package com.cj.exclematerial.aop;

import com.cj.exclematerial.annotation.FileName;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 返回结果的AOP
 */
@Aspect
@Component
public class ResultAop {
/*    @Pointcut("execution(public * com.cj.exclematerial.controller.*.*Excel*(..))")
    private void controller(){}*/

    @Pointcut("@annotation(name)")
    public void fileName(FileName name) {}
/*    @Pointcut("execution(public * com.cj.exclematerial.serveice.*.*(..))")
    private void serveice(){}*/
    /**
     * 设置响应头信息
     * @param joinPoint
     */
    @Before("fileName(name)")
    public void setResponse(JoinPoint joinPoint,FileName name){
        Object[] args = joinPoint.getArgs(); //获取目标对象方法参数
        for (int i = 0; i <args.length ; i++) {
            if (args[i] instanceof HttpServletResponse){
                HttpServletResponse response=(HttpServletResponse) args[i];
                response.reset();
                response.setHeader("Content-disposition", "attachment; filename="+name.value());
                response.setContentType("application/msexcel");
            }
        }
    }

    /**
     * 关闭流资源
     * @param joinPoint
     */
    @After("fileName(name)")
    public void closeOutputStream(JoinPoint joinPoint,FileName name) throws IOException {
        Object[] args = joinPoint.getArgs(); //获取目标对象方法参数
        for (int i = 0; i <args.length ; i++) {
            if (args[i] instanceof HttpServletResponse){
                HttpServletResponse response=(HttpServletResponse) args[i];
                OutputStream outputStream=(OutputStream) response.getOutputStream();
                outputStream.close();
            }
        }
    }

}
