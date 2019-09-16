package com.cj.exclegrade.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.FIELD})//只作用在字段上
@Retention(RetentionPolicy.RUNTIME)//运行时有效
@Documented
public @interface ExcelStyle {

}
