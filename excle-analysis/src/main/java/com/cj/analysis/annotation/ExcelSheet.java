package com.cj.analysis.annotation;


import java.lang.annotation.*;

@Target({ElementType.TYPE})//只作用在字段上
@Retention(RetentionPolicy.RUNTIME)//运行时有效
@Documented
public @interface ExcelSheet {
    String value();
}
