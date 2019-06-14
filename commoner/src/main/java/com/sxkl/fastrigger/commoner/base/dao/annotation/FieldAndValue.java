package com.sxkl.fastrigger.commoner.base.dao.annotation;

import com.sxkl.fastrigger.commoner.base.entity.BaseEntity;
import lombok.Data;

/**
 * 系统公用模块 实体属性名称和值
 * @author wy
 * @date 2019-06-12 10:26:59
 */
@Data
public class FieldAndValue<Entity extends BaseEntity> {

    private String name;
    private Object value;
    protected Entity sourceEntity;
    protected boolean cascade = false;
    protected boolean valid = true;

    public FieldAndValue(Entity sourceEntity) {
        this.sourceEntity = sourceEntity;
    }
}
