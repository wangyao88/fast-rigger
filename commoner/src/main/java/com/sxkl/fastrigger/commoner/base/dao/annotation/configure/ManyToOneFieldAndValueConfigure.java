package com.sxkl.fastrigger.commoner.base.dao.annotation.configure;

import com.sxkl.fastrigger.commoner.base.annotation.ManyToOne;
import com.sxkl.fastrigger.commoner.base.annotation.OneToOne;
import com.sxkl.fastrigger.commoner.base.dao.annotation.FieldAndValue;
import com.sxkl.fastrigger.commoner.base.entity.BaseEntity;

import java.lang.reflect.Field;

/**
 * 系统公用模块 FieldAndValue装配器
 * @author wy
 * @date 2019-06-14 10:45:52
 */
public class ManyToOneFieldAndValueConfigure extends AbstractFieldAndValueConfigure {

    @Override
    protected <Entity extends BaseEntity> FieldAndValue<Entity> convert(Entity entity, Field field) {
        FieldAndValue<Entity> fieldAndValue = new FieldAndValue<>(null);
        Entity manyToOneEntity = null;
        try {
            manyToOneEntity = (Entity) field.get(entity);
            if(entity.getSource().getClass() == manyToOneEntity.getClass()) {
                fieldAndValue.setValid(false);
                return fieldAndValue;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            fieldAndValue.setValid(false);
            return fieldAndValue;
        }
        ManyToOne[] manyToOnes = field.getAnnotationsByType(ManyToOne.class);
        ManyToOne manyToOne = manyToOnes[0];
        boolean cascade = manyToOne.cascade();
        fieldAndValue.setCascade(cascade);
        if(!cascade) {
            return fieldAndValue;
        }
        fieldAndValue.setSourceEntity(manyToOneEntity);
        fieldAndValue.setValue(manyToOneEntity.getId());
        fieldAndValue.setName(manyToOne.fk());
        return fieldAndValue;
    }
}
