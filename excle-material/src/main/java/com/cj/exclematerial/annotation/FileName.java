package com.cj.exclematerial.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD})//只作用在字段上
@Retention(RetentionPolicy.RUNTIME)//运行时有效
@Documented
public @interface FileName {
    String value();
}
