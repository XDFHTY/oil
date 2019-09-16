package com.cj.common.exception;

import com.cj.core.domain.ApiResult;

/**
 * 自定义用户异常
 * service抛出此异常即可
 */
public class UserException extends BaseBusinessException { // 继承继承 业务异常类

    public UserException(ApiResult apiResult) {
        super(apiResult);
    }
}
