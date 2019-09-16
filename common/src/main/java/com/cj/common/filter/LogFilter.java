package com.cj.common.filter;


import com.cj.common.exception.UserException;
import com.cj.core.domain.ApiResult;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long t1 = System.currentTimeMillis();
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        ResponseWrapper wrapperResponse = new ResponseWrapper(response);//转换成代理类
        // 这里只拦截返回，直接让请求过去，如果在请求前有处理，可以在这里处理

        Gson gson = new Gson();

        filterChain.doFilter(servletRequest, wrapperResponse);
        String ciphertext = "";


        byte[] content = wrapperResponse.getContent();//获取返回值
        //判断是否有值
        if (content.length > 0) {

            String str = new String(content, "UTF-8");
//            System.out.println("=============filter获取的返回值=================="+str);
            ApiResult apiResult = new ApiResult();
            try {
                apiResult = gson.fromJson(str, ApiResult.class);

                long t2 = System.currentTimeMillis();
                try {
                    //......根据需要处理返回值
                    if (apiResult.getData() == null) {
                        apiResult.setData("");
                    }


                    logger.info("Filter耗时: " + (t2 - t1) + " ms!");
                    apiResult.setParams("filter层耗时：" + (t2 - t1) + "ms");
                    ciphertext = gson.toJson(apiResult);


                } catch (Exception e) {
                    e.printStackTrace();
                }


//                logger.info("返回数据: " + ciphertext);
                logger.info("执行完成 :==========================================================================================");
                //把返回值输出到客户端
                ServletOutputStream out = servletResponse.getOutputStream();
                out.write(ciphertext.getBytes());
                out.flush();
            } catch (Exception e) {
                //把返回值输出到客户端
                ServletOutputStream out = servletResponse.getOutputStream();
                out.write(content);
                out.flush();
            }


        }

    }

    @Override
    public void destroy() {

    }


}
