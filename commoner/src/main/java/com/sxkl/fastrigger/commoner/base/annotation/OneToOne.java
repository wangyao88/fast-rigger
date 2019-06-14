package com.sxkl.fastrigger.commoner.base.annotation;

import java.lang.annotation.*;

/**
 * 系统公用模块 集合属性查询
 * 根据所在实体的主键去属性对应表去查询数据
 * @author wy
 * @date 2019-06-12 09:49:02
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OneToOne {

    String fk();
    boolean cascade() default false;
}
