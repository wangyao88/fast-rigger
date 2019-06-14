package com.sxkl.fastrigger.commoner.base.dao.annotation.configure;

import com.google.common.collect.Lists;
import com.sxkl.fastrigger.commoner.base.annotation.OneToOne;
import com.sxkl.fastrigger.commoner.base.dao.annotation.FieldAndValue;
import com.sxkl.fastrigger.commoner.base.entity.BaseEntity;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 系统公用模块 FieldAndValue装配器
 * @author wy
 * @date 2019-06-14 11:11:01
 */
public class OneToManyFieldAndValueConfigure extends AbstractFieldAndValueConfigure {

    @Override
    protected <Entity extends BaseEntity> List<FieldAndValue<Entity>> convert(Entity entity, Field field) {
        FieldAndValue<Entity> fieldAndValue = new FieldAndValue<>(null);

        return Lists.newArrayList(fieldAndValue);
    }
}
