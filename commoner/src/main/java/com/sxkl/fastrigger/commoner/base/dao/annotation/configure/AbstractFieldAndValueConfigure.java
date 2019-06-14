package com.sxkl.fastrigger.commoner.base.dao.annotation.configure;

import com.sxkl.fastrigger.commoner.base.dao.annotation.FieldAndValue;
import com.sxkl.fastrigger.commoner.base.entity.BaseEntity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统公用模块 FieldAndValue装配器
 * @author wy
 * @date 2019-06-13 16:02:49
 */
public abstract class AbstractFieldAndValueConfigure{

    public <Entity extends BaseEntity, MyAnnotation extends Annotation> List<FieldAndValue<Entity>> configure(Entity entity, List<Field> fields, Class<MyAnnotation> myAnnotationClass) {
        return fields.stream()
                     .filter(field -> isRightAnnotation(field, myAnnotationClass))
                     .map(field -> convert(entity, field))
                     .flatMap(Collection::stream)
                     .filter(FieldAndValue::isValid)
                     .collect(Collectors.toList());
    }

    protected abstract <Entity extends BaseEntity> List<FieldAndValue<Entity>> convert(Entity entity, Field field);

    private <MyAnnotation extends Annotation> boolean isRightAnnotation(Field field, Class<MyAnnotation> myAnnotationClass) {
        MyAnnotation[] annotations = field.getAnnotationsByType(myAnnotationClass);
        return annotations != null && annotations.length > 0;
    }
}

