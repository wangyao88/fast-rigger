package com.sxkl.fastrigger.commoner.base.annotation;

import java.lang.annotation.*;

/**
 * 系统公用模块sql条件注解 = > >= < <= like
 * @author wy
 * @date 2019-06-10 11:14:52
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Condition {

    String value() default "=";
}
