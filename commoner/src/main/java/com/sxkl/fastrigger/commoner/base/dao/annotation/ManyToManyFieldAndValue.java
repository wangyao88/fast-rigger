package com.sxkl.fastrigger.commoner.base.dao.annotation;

import com.sxkl.fastrigger.commoner.base.entity.BaseEntity;
import lombok.Data;

/**
 * 系统公用模块 ManyToMany实体属性名称和值
 * @author wy
 * @date 2019-06-13 14:49:03
 */
@Data
public class ManyToManyFieldAndValue<Entity extends BaseEntity> extends FieldAndValue<Entity> {

    private String middleTableName;
    private String myIdName;
    private String myIdValue;
    private String otherIdName;
    private String otherIdValue;

    public ManyToManyFieldAndValue(Entity sourceEntity) {
        super(sourceEntity);
    }
}
