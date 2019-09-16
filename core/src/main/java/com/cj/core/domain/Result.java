package com.cj.core.domain;

import lombok.Getter;

/**
 * Created by XD on 2018/11/26.
 */
@Getter
public enum Result {




    //1000以内是系统错误，
    //100-511为http 状态码
//    CODE_200(200, "操作成功！！"),
    CODE_401(401, "未授权"),
    CODE_400(400, "请求参数错误，无法解析"),
    CODE_403(403, "服务器拒绝请求(权限不足，请联系管理人员)"),
    CODE_404(404, "资源不存在"),
    CODE_405(405, "请求方式有误"),
    CODE_417(417, "执行失败"),
    //    CODE_409(409, "冲突"),
    CODE_500(500, "服务器内部错误"),
    // --- 5xx Server Error ---

    // --- 8xx common error ---



    //1000 为通用失败
    FAIL(1000,"处理失败"),
    //1001 为通用成功
    SUCCESS(1001,"处理成功"),

    ;


    /*两个属性*/

    private int code = 0;
    private String msg = null;



    /*构造函数*/
    private Result() {
    }
    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
