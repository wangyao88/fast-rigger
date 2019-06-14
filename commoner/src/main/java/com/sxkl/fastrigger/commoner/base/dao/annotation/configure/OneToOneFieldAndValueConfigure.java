package com.sxkl.fastrigger.commoner.base.dao.annotation.configure;

import com.google.common.collect.Lists;
import com.sxkl.fastrigger.commoner.base.annotation.OneToOne;
import com.sxkl.fastrigger.commoner.base.dao.annotation.FieldAndValue;
import com.sxkl.fastrigger.commoner.base.entity.BaseEntity;
import com.sxkl.fastrigger.commoner.utils.ObjectUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 系统公用模块 FieldAndValue装配器
 * @author wy
 * @date 2019-06-13 16:02:49
 */
public class OneToOneFieldAndValueConfigure extends AbstractFieldAndValueConfigure {

    @Override
    protected <Entity extends BaseEntity> List<FieldAndValue<Entity>> convert(Entity entity, Field field) {
        FieldAndValue<Entity> fieldAndValue = new FieldAndValue<>(null);
        Entity oneToOneEntity = null;
        try {
            oneToOneEntity = (Entity) field.get(entity);
            if(ObjectUtils.isNotNull(entity.getSource()) && entity.getSource().getClass() == oneToOneEntity.getClass()) {
                fieldAndValue.setValid(false);
                return Lists.newArrayList(fieldAndValue);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            fieldAndValue.setValid(false);
            return Lists.newArrayList(fieldAndValue);
        }
        OneToOne[] oneToOnes = field.getAnnotationsByType(OneToOne.class);
        OneToOne oneToOne = oneToOnes[0];
        boolean cascade = oneToOne.cascade();
        fieldAndValue.setCascade(cascade);
        if(!cascade) {
            return Lists.newArrayList(fieldAndValue);
        }
        fieldAndValue.setSourceEntity(oneToOneEntity);
        fieldAndValue.setValue(oneToOneEntity.getId());
        fieldAndValue.setName(oneToOne.fk());
        return Lists.newArrayList(fieldAndValue);
    }
}
