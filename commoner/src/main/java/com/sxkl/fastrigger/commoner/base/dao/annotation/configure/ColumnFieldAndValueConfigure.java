package com.sxkl.fastrigger.commoner.base.dao.annotation.configure;

import com.sxkl.fastrigger.commoner.base.dao.annotation.FieldAndValue;
import com.sxkl.fastrigger.commoner.base.entity.BaseEntity;

import java.lang.reflect.Field;

/**
 * 系统公用模块 FieldAndValue装配器
 * @author wy
 * @date 2019-06-13 16:02:49
 */
public class ColumnFieldAndValueConfigure extends AbstractFieldAndValueConfigure{

    @Override
    protected <Entity extends BaseEntity> FieldAndValue<Entity> convert(Entity entity, Field field) {
        FieldAndValue<Entity> fieldAndValue = new FieldAndValue<>(entity);
        fieldAndValue.setName(field.getName());
        try {
            fieldAndValue.setValue(field.get(entity));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fieldAndValue;
    }
}
