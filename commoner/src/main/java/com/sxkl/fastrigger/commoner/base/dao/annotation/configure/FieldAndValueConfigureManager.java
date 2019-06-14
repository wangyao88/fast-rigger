package com.sxkl.fastrigger.commoner.base.dao.annotation.configure;

import com.google.common.collect.Maps;
import com.sxkl.fastrigger.commoner.base.dao.annotation.FieldAndValue;
import com.sxkl.fastrigger.commoner.base.entity.BaseEntity;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 系统公用模块 FieldAndValue装配器
 * @author wy
 * @date 2019-06-13 16:02:49
 */
public class FieldAndValueConfigureManager {

    private static Map<String, AbstractFieldAndValueConfigure> configures = Maps.newHashMap();

    static {
        Reflections reflections = new Reflections(FieldAndValueConfigureManager.class.getPackage().getName());
        Set<Class<? extends AbstractFieldAndValueConfigure>> fieldAndValueConfigureClazzs = reflections.getSubTypesOf(AbstractFieldAndValueConfigure.class);
        try {
            for(Class<? extends AbstractFieldAndValueConfigure> clazz : fieldAndValueConfigureClazzs) {
                String name = clazz.getSimpleName().replaceAll("FieldAndValueConfigure", "");
                configures.put(name,clazz.newInstance());
            }
            System.out.println(configures.size());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static <Entity extends BaseEntity, MyAnnotation extends Annotation> List<FieldAndValue<Entity>> configure(Entity entity, List<Field> fields, Class<MyAnnotation> myAnnotationClass) {
        return configures.get(myAnnotationClass.getSimpleName()).configure(entity, fields, myAnnotationClass);
    }
}
