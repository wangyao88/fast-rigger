package com.sxkl.fastrigger.commoner.base.annotation;

import java.lang.annotation.*;

/**
 * 系统公用模块 属性延迟加载
 * @author wy
 * @date 2019-06-10 11:14:52
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {

    String name();
}
