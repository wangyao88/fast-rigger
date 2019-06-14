package com.sxkl.fastrigger.commoner.base.dao.annotation.configure;

import com.sxkl.fastrigger.commoner.base.annotation.OneToOne;
import com.sxkl.fastrigger.commoner.base.dao.annotation.FieldAndValue;
import com.sxkl.fastrigger.commoner.base.entity.BaseEntity;

import java.lang.reflect.Field;

/**
 * 系统公用模块 FieldAndValue装配器
 * @author wy
 * @date 2019-06-13 16:02:49
 */
public class OneToOneFieldAndValueConfigure extends AbstractFieldAndValueConfigure {

    @Override
    protected <Entity extends BaseEntity> FieldAndValue<Entity> convert(Entity entity, Field field) {
        FieldAndValue<Entity> fieldAndValue = new FieldAndValue<>(null);
        Entity oneToOneEntity = null;
        try {
            oneToOneEntity = (Entity) field.get(entity);
            if(entity.getSource().getClass() == oneToOneEntity.getClass()) {
                fieldAndValue.setValid(false);
                return fieldAndValue;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            fieldAndValue.setValid(false);
            return fieldAndValue;
        }
        OneToOne[] oneToOnes = field.getAnnotationsByType(OneToOne.class);
        OneToOne oneToOne = oneToOnes[0];
        boolean cascade = oneToOne.cascade();
        fieldAndValue.setCascade(cascade);
        if(!cascade) {
            return fieldAndValue;
        }
        fieldAndValue.setSourceEntity(oneToOneEntity);
        fieldAndValue.setValue(oneToOneEntity.getId());
        fieldAndValue.setName(oneToOne.fk());
        return fieldAndValue;
    }
}
