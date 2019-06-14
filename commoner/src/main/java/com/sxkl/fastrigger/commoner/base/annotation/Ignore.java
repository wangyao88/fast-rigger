package com.sxkl.fastrigger.commoner.base.annotation;

import java.lang.annotation.*;

/**
 * 系统公用模块 忽略属性，不参与解析
 * @author wy
 * @date 2019-06-12 09:28:23
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Ignore {

}
