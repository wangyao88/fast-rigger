package com.sxkl.fastrigger.commoner.base.annotation;

import java.lang.annotation.*;

/**
 * 系统公用模块 属性映射注解
 * @author wy
 * @date 2019-06-10 11:14:52
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {

    String value();
}
