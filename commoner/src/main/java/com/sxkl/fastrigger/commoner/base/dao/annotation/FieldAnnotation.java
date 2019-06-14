package com.sxkl.fastrigger.commoner.base.dao.annotation;

import com.google.common.collect.Lists;
import com.sxkl.fastrigger.commoner.base.entity.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * 系统公用模块 实体属性对应注解解析实体
 * @author wy
 * @date 2019-06-12 10:05:44
 */
@Data
public class FieldAnnotation<Entity extends BaseEntity> {

    private List<FieldAndValue<Entity>> id = Lists.newArrayList();
    private List<FieldAndValue<Entity>> columns = Lists.newArrayList();
    private List<FieldAndValue<Entity>> oneToOnes = Lists.newArrayList();
    private List<FieldAndValue<Entity>> oneToManys = Lists.newArrayList();
    private List<FieldAndValue<Entity>> ManyToOnes = Lists.newArrayList();
    private List<FieldAndValue<Entity>> ManyToManys = Lists.newArrayList();
}
