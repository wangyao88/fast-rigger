package com.sxkl.fastrigger.commoner.base.annotation;

import java.lang.annotation.*;

/**
 * 系统公用模块 主键注解 属性不能为空
 * @author wy
 * @date 2019-06-12 09:28:23
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Id {

    String column();
}
