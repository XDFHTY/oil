package com.cj.common.exception;

import com.cj.core.domain.ApiResult;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *  @description 全局异常处理: 使用 @RestControllerAdvice + @ExceptionHandler 注解方式实现全局异常处理
 *
 */
@RestControllerAdvice
@RestController
@ApiIgnore
public class GlobalExceptionHandler implements ErrorController {

    private final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);


    /**
     *  系统异常处理
     * @param e 异常
     * @return
     */
    @ExceptionHandler({Exception.class})    //申明捕获那个异常类
    public ResponseEntity globalExceptionHandler(Exception e, HttpServletRequest request) {
        this.logger.error(e.getMessage(), e);

        ApiResult apiResult = null;
        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            apiResult = ApiResult.CODE_404();
            return  ResponseEntity.status(404).body(apiResult);
        }else if (e instanceof com.cj.common.security.exception.MyAccessMethodDeniedException){

            apiResult = ApiResult.CODE_401();
            apiResult.setMsg(e.getMessage());
            return  ResponseEntity.status(401).body(apiResult);

        }else  if (e instanceof org.springframework.web.HttpRequestMethodNotSupportedException ){
            apiResult = ApiResult.CODE_405();
            return  ResponseEntity.status(405).body(apiResult);
        }else if ( e instanceof com.google.gson.JsonSyntaxException
                || e instanceof com.fasterxml.jackson.databind.JsonMappingException
                || e instanceof org.springframework.http.converter.HttpMessageNotReadableException){
            apiResult = ApiResult.CODE_400();
            return  ResponseEntity.status(400).body(apiResult);
        }else if (e instanceof org.springframework.dao.DuplicateKeyException){
            apiResult = ApiResult.FAIL();
            if ("com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException".equals(e.getCause().getClass().getName())){
                apiResult.setMsg("每个场站/管线 每年只能添加一张表");

            }else {
                apiResult.setMsg("数据库操作异常");
            }
            return ResponseEntity.status(200).body(apiResult);
        }
        else {
            apiResult = ApiResult.CODE_500();
            return  ResponseEntity.status(500).body(apiResult);
        }

    }




    /**
     * 捕获业务异常
     * @param e
     * @return
     */
    @ExceptionHandler({BaseBusinessException.class})
    public ResponseEntity BusinessExceptionHandler(BaseBusinessException e) {

        this.logger.error(e);
        ApiResult apiResult = ApiResult.FAIL();
        apiResult.setCode(e.getCode());
        apiResult.setMsg(e.getMessage());
        if (e.getCode() > 500){

            return ResponseEntity.status(200).body(apiResult);
        }else {
            return ResponseEntity.status(e.getCode()).body(apiResult);

        }

    }

    /**
     * 捕获404
     */

    private static final String ERROR_PATH = "/error";

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }


    @RequestMapping(value = ERROR_PATH)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiResult handleError(HttpServletRequest request) {



        ApiResult apiResult = ApiResult.CODE_404();
        return apiResult;
    }

}
