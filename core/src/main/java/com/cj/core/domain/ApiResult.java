package com.cj.core.domain;

import com.google.common.collect.ImmutableMap;
import lombok.Data;

import java.util.Map;
@Data
public class ApiResult {

    private int code;

    private String msg;

    private Object data;

    private Object params;


    public static ApiResult SUCCESS() {
        ApiResult apiResult = new ApiResult();
        Result success = Result.SUCCESS;
        apiResult.code = success.getCode();
        apiResult.msg = success.getMsg();
        return apiResult;

    }
    public static ApiResult FAIL() {
        ApiResult apiResult = new ApiResult();
        Result success = Result.FAIL;
        apiResult.code = success.getCode();
        apiResult.msg = success.getMsg();
        return apiResult;

    }
    public static ApiResult CODE_401() {
        ApiResult apiResult = new ApiResult();
        Result success = Result.CODE_401;
        apiResult.code = success.getCode();
        apiResult.msg = success.getMsg();
        return apiResult;

    }
    public static ApiResult CODE_400() {
        ApiResult apiResult = new ApiResult();
        Result success = Result.CODE_400;
        apiResult.code = success.getCode();
        apiResult.msg = success.getMsg();
        return apiResult;

    }
    public static ApiResult CODE_403() {
        ApiResult apiResult = new ApiResult();
        Result success = Result.CODE_403;
        apiResult.code = success.getCode();
        apiResult.msg = success.getMsg();
        return apiResult;

    }
    public static ApiResult CODE_404() {
        ApiResult apiResult = new ApiResult();
        Result success = Result.CODE_404;
        apiResult.code = success.getCode();
        apiResult.msg = success.getMsg();
        return apiResult;

    }
    public static ApiResult CODE_405() {
        ApiResult apiResult = new ApiResult();
        Result success = Result.CODE_405;
        apiResult.code = success.getCode();
        apiResult.msg = success.getMsg();
        return apiResult;

    }
    public static ApiResult CODE_417() {
        ApiResult apiResult = new ApiResult();
        Result success = Result.CODE_417;
        apiResult.code = success.getCode();
        apiResult.msg = success.getMsg();
        return apiResult;

    }
    public static ApiResult CODE_500() {
        ApiResult apiResult = new ApiResult();
        Result success = Result.CODE_500;
        apiResult.code = success.getCode();
        apiResult.msg = success.getMsg();
        return apiResult;

    }




    public Map<String, Object> toMap() {

        return ImmutableMap.<String, Object>builder()
                .put("code", code)
                .put("msg", msg)
                .put("data", data==null?"":data)
                .put("params", params==null?"":params)
                .build();
    }


}
