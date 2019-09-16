package com.cj.common.exception;

import com.cj.core.domain.ApiResult;

/**
 *  业务异常父类
 */
public class BaseBusinessException extends RuntimeException {

    private Integer code;


    // 给子类用的方法
    public BaseBusinessException(ApiResult apiResult) {

        this(apiResult.getMsg(), apiResult.getCode());
    }

    private BaseBusinessException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}