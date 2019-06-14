package com.sxkl.fastrigger.commoner.base.dao.annotation.configure;

import com.google.common.collect.Lists;
import com.sxkl.fastrigger.commoner.base.annotation.ManyToOne;
import com.sxkl.fastrigger.commoner.base.dao.annotation.FieldAndValue;
import com.sxkl.fastrigger.commoner.base.entity.BaseEntity;
import com.sxkl.fastrigger.commoner.utils.ObjectUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 系统公用模块 FieldAndValue装配器
 * @author wy
 * @date 2019-06-14 10:45:52
 */
public class ManyToOneFieldAndValueConfigure extends AbstractFieldAndValueConfigure {

    @Override
    protected <Entity extends BaseEntity> List<FieldAndValue<Entity>> convert(Entity entity, Field field) {
        FieldAndValue<Entity> fieldAndValue = new FieldAndValue<>(null);
        Entity manyToOneEntity = null;
        try {
            manyToOneEntity = (Entity) field.get(entity);
            if(ObjectUtils.isNotNull(entity.getSource()) && entity.getSource().getClass() == manyToOneEntity.getClass()) {
                fieldAndValue.setValid(false);
                return Lists.newArrayList(fieldAndValue);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            fieldAndValue.setValid(false);
            return Lists.newArrayList(fieldAndValue);
        }
        ManyToOne[] manyToOnes = field.getAnnotationsByType(ManyToOne.class);
        ManyToOne manyToOne = manyToOnes[0];
        boolean cascade = manyToOne.cascade();
        fieldAndValue.setCascade(cascade);
        if(!cascade) {
            return Lists.newArrayList(fieldAndValue);
        }
        fieldAndValue.setSourceEntity(manyToOneEntity);
        fieldAndValue.setValue(manyToOneEntity.getId());
        fieldAndValue.setName(manyToOne.fk());
        return Lists.newArrayList(fieldAndValue);
    }
}
