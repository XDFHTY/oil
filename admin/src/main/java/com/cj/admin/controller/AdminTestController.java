package com.cj.admin.controller;

import com.cj.common.entity.Admin;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.Pager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/test")
@Api(tags = "测试")
@ApiIgnore
public class AdminTestController {


    @GetMapping("/t")
    public ResponseEntity test(HttpServletRequest request, HttpServletResponse response) throws Exception {



//        response.sendRedirect("http://192.168.0.109:8085/static/test.html");

//        return new ModelAndView(new RedirectView("http://192.168.0.109:8085/static/test.html"));
        ApiResult apiResult = ApiResult.FAIL();
        apiResult.setMsg("测试业务异常捕获");

//        throw new UserException(apiResult);

        throw new Exception();

//        throw new MyAccessMethodDeniedException("用户未提交token");

//        return ApiResult.SUCCESS;
    }

    @GetMapping("/t2")
    @ApiOperation("测试2")
    public void Test2(@ModelAttribute Pager<Admin> pager){

        System.out.println(pager);
    }
}
