package com.sxkl.fastrigger.commoner.base.dao.annotation;

import com.google.common.collect.Lists;
import com.sxkl.fastrigger.commoner.base.annotation.Column;
import com.sxkl.fastrigger.commoner.base.annotation.Id;
import com.sxkl.fastrigger.commoner.base.annotation.Ignore;
import com.sxkl.fastrigger.commoner.base.annotation.OneToOne;
import com.sxkl.fastrigger.commoner.base.dao.annotation.configure.FieldAndValueConfigureManager;
import com.sxkl.fastrigger.commoner.base.entity.BaseEntity;
import com.sxkl.fastrigger.commoner.utils.ObjectUtils;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统公用模块 实体属性对应注解解析器
 * @author wy
 * @date 2019-06-12 10:08:55
 */
public class FieldAnnotationParser {


    public static <Entity extends BaseEntity> FieldAnnotation parse(Entity entity) throws SQLException {
        FieldAnnotation<Entity> fieldAnnotation = new FieldAnnotation<>();
        Field[] declaredFields = entity.getClass().getDeclaredFields();
        List<Field> fieldsList = Lists.newArrayList(declaredFields);
        List<Field> fields = fieldsList.stream().filter(FieldAnnotationParser::notIgnore).filter(field -> notNull(entity, field)).collect(Collectors.toList());

        List<FieldAndValue<Entity>> ids = FieldAndValueConfigureManager.configure(entity, fields, Id.class);
        if(ids.size() != 1) {
            throw new SQLException("Id注解有且只能有一个，且只能为BaseEntity的属性Id");
        }
        fieldAnnotation.setId(ids);

        List<FieldAndValue<Entity>> columns = FieldAndValueConfigureManager.configure(entity, fields, Column.class);
        fieldAnnotation.setColumns(columns);

        List<FieldAndValue<Entity>> oneToOnes = FieldAndValueConfigureManager.configure(entity, fields, OneToOne.class);
        fieldAnnotation.setOneToOnes(oneToOnes);

        List<FieldAndValue<Entity>> ManyToOnes = Lists.newArrayList();



        List<FieldAndValue<Entity>> oneToManys = Lists.newArrayList();
        List<FieldAndValue<Entity>> ManyToManys = Lists.newArrayList();


        return fieldAnnotation;
    }

    private static boolean notIgnore(Field field) {
        Ignore[] ignores = field.getAnnotationsByType(Ignore.class);
        return ignores == null || ignores.length == 0;
    }

    private static <Entity extends BaseEntity> boolean notNull(Entity entity, Field field) {
        if(field.getType().isPrimitive()) {
            try {
                Object obj = field.get(entity);
                return ObjectUtils.isNotNull(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        }
        if(field.getType() == List.class) {
            try {
                List list = (List) field.get(entity);
                return ObjectUtils.isNotEmpty(list);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
