package com.cj.exclematerial.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})//只作用在字段上
@Retention(RetentionPolicy.RUNTIME)//运行时有效
@Documented
public @interface ExcelField {
    /**
     * 列名
     * @return
     */
    String value() default "未知列名";

    boolean flag() default false;

    String className() default "";
}
